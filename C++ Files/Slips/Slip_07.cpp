// Q1.Write a C++ program using class which contains two data members as type integer. Create and
// initialize the objects using default constructor, parameterized constructor with default value.
// Write a member function to display maximum from given two numbers for all objects. 


#include<iostream>
using namespace std;

class Number{

    public :
        int a;
        int b;
        // Default constructor
        Number(){
            cout<<"Enter num1 & num2 : ";
            cin>>a>>b;

        }

        // Parameterized constructor
        Number(int a, int b): a(a), b(b){}
     
        void Max(){
            cout<<"Max : "<<((a > b) ? a : b)<<endl; 
        }
};
int main(){
    Number n1;
    n1.Max();

    Number n2(5,10);
    n2.Max();

}



// Q2.Create a class Matrix and Write a C++ program to perform following functions:
// a. To accept a Matrix
// b. To display a Matrix
// c. Overload unary minus ‘–‘ operator to calculate transpose of a Matrix
// d. Overload binary multiplication '*’ operator to calculate multiplication of two matrices

#include <iostream>
using namespace std;

class Matrix {
private:
    int rows, cols;
    int** mat;

public:
    Matrix(int r, int c) : rows(r), cols(c) {
        mat = new int*[rows];
        for (int i = 0; i < rows; i++)
            mat[i] = new int[cols](); // Initialize to 0
    }

    ~Matrix() {
        for (int i = 0; i < rows; i++)
            delete[] mat[i];
        delete[] mat;
    }

    void accept() {
        cout << "Enter matrix elements (" << rows << "x" << cols << "):" << endl;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cin >> mat[i][j];
    }

    void display() const {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                cout << mat[i][j] << " ";
            cout << endl;
        }
    }

    Matrix operator-() const {
        Matrix result(cols, rows);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.mat[j][i] = mat[i][j];
        return result;
    }

    Matrix operator*(const Matrix& other) const {
        Matrix result(rows, other.cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < other.cols; j++)
                for (int k = 0; k < cols; k++)
                    result.mat[i][j] += mat[i][k] * other.mat[k][j];
        return result;
    }
};

int main() {
    int r1, c1, r2, c2;

    cout << "Enter dimensions of first matrix (rows columns): ";
    cin >> r1 >> c1;
    Matrix mat1(r1, c1);
    mat1.accept();

    cout << "Enter dimensions of second matrix (rows columns): ";
    cin >> r2 >> c2;
    Matrix mat2(r2, c2);
    mat2.accept();

    cout << "\nFirst Matrix:" << endl;
    mat1.display();

    cout << "\nSecond Matrix:" << endl;
    mat2.display();

    cout << "\nTranspose of First Matrix:" << endl;
    (-mat1).display();

    cout << "\nMultiplication of First and Second Matrices:" << endl;
    (mat1 * mat2).display();

    return 0;
}


// Q2.Create a class College containing data members as College_Id, College_Name,
// Establishment_year, University_Name. Write a C++ program with following functions
// a. Accept n College details
// b. Display College details of specified University
// c. Display College details according to Establishment year (Use Array of Objects and
// Function Overloading). 