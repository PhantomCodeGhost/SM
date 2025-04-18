/*
 * Copyright (C) 2020 The exTHmUI Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by @phantomcodeghost law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cjybyjk.statuslyricext.preferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ListView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import java.util.Arrays;
import java.util.ArrayList;

import io.cjybyjk.statuslyricext.R;
import io.cjybyjk.statuslyricext.misc.Constants;
import io.cjybyjk.statuslyricext.preferences.PackageListAdapter.PackageItem;

public class PackageListPreference extends PreferenceCategory implements
        Preference.OnPreferenceClickListener {

    private final Context mContext;

    private final PackageListAdapter mPackageAdapter;
    private final PackageManager mPackageManager;

    private final Preference mAddPackagePref;

    private final ArrayList<String> mPackages = new ArrayList<>();

    public PackageListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mPackageManager = mContext.getPackageManager();
        mPackageAdapter = new PackageListAdapter(mContext);

        mAddPackagePref = makeAddPref();

        this.setOrderingAsAdded(false);
    }

    private Preference makeAddPref() {
        Preference pref = new Preference(mContext);
        pref.setTitle(R.string.add_package_to_title);
        pref.setIcon(R.drawable.ic_add);
        pref.setPersistent(false);
        pref.setOnPreferenceClickListener(this);
        return pref;
    }

    private ApplicationInfo getAppInfo(String packageName) {
        try {
            return mPackageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parsePackageList() {
        mPackages.clear();
        String packageListData = getPersistedString("");
        if (!TextUtils.isEmpty(packageListData)) {
            String[] packageListArray = packageListData.split(";");
            mPackages.addAll(Arrays.asList(packageListArray));
        }
    }

    private void refreshCustomApplicationPrefs() {
        parsePackageList();
        removeAll();
        addPreference(mAddPackagePref);
        for (String pkg : mPackages) {
            addPackageToPref(pkg);
        }
    }

    private void savePackagesList() {
        String packageListData;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            packageListData = String.join(";", mPackages);
        } else {
            StringBuilder sb = new StringBuilder();
            for (String pkg : mPackages) {
                sb.append(pkg).append(';');
            }
            packageListData = sb.toString();
        }
        persistString(packageListData);
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(new Intent(Constants.BROADCAST_IGNORED_APP_CHANGED));
    }

    private void addPackageToPref(String packageName) {
        Preference pref = new Preference(mContext);
        ApplicationInfo appInfo = getAppInfo(packageName);
        if (appInfo == null) return;
        pref.setKey(packageName);
        pref.setTitle(appInfo.loadLabel(mPackageManager));
        pref.setIcon(appInfo.loadIcon(mPackageManager));
        pref.setPersistent(false);
        pref.setOnPreferenceClickListener(this);
        addPreference(pref);
    }

    private void addPackageToList(String packageName) {
        if (!mPackages.contains(packageName)) {
            mPackages.add(packageName);
            addPackageToPref(packageName);
            savePackagesList();
        }
    }

    private void removePackageFromList(String packageName) {
        mPackages.remove(packageName);
        savePackagesList();
    }

    @Override
    public void onAttached() {
        super.onAttached();
        refreshCustomApplicationPrefs();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (preference == mAddPackagePref) {
            ListView appsList = new ListView(mContext);
            appsList.setAdapter(mPackageAdapter);
            builder.setTitle(R.string.profile_choose_app);
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.setView(appsList);
            final Dialog dialog = builder.create();
            appsList.setOnItemClickListener((parent, view, position, id) -> {
                PackageItem info = (PackageItem) parent.getItemAtPosition(position);
                addPackageToList(info.packageName);
                dialog.cancel();
            });
            dialog.show();
        } else if (preference == findPreference(preference.getKey())) {
            builder.setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_message)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    removePackageFromList(preference.getKey());
                    removePreference(preference);
                })
                .setNegativeButton(android.R.string.cancel, null).show();
        }
        return true;
    }

}
