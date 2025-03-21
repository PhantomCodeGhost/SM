<!-- Q1. Design a HTML form to accept a string. Write a PHP function that checks whether a
passed string is a palindrome or not?  -->
<?php

function isPalindrome($str){
    $str = strtolower($str);  // Convert to lowercase
    return $str == strrev($str);  // Check if the string is the same when reversed
}
$showResult = "";
$result = "";
if (isset($_POST['str'])) {
    // Sanitize user input to avoid XSS attacks
    $input = $_POST['str'];
    $result = isPalindrome($input);
    $showResult = True;
}

?>

<html>
<body>
    <form method="Post">
        <input type="text" name="str" placeholder="Enter a String" required>
        <button>Check</button>
    </form>
    
    <?php 
    // Only show result after form submission
        if($showResult){
        if ($result) {
            echo "<p>The string $input is a Palindrome.</p>";
        } else {
            echo "<p>The string $input is not a Palindrome.</p>";
        }
    }
    ?>
</body>
</html>


<!-- Q2. Declare a Multidimensional Array. Display specific element from a Multidimensional
array. Also delete given element from the Multidimensional array.(After each operation
display array content.) -->


<?php
// Declare a multidimensional array
$students = array(
    array("John", 22, "Math"),
    array("Jane", 23, "Science"),
    array("Doe", 24, "History")
);

// Display the original array
echo "Original Array: <br>";
print_r($students);
echo "<br><br>";

// Display a specific element (name of the second student)
echo "Specific Element (Second Student Name): " . $students[1][0] . "<br><br>";

// Delete the second student
unset($students[1]);

// Display the array after deletion
echo "Array After Deletion: <br>";
print_r($students);
echo "<br><br>";
?>

