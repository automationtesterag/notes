# Methods in Java (Module-2) – Proper Notes

## What is a Method?

A method is a block of Java statements enclosed within curly braces `{ }` that performs a specific task and gets executed whenever it is called.

### Definition

* A method contains a set of statements.
* It is used to perform a particular operation.
* A method executes only when it is called.
* The main advantage of methods is **reusability**.

---

# Method Syntax

```java
AccessModifier NonAccessModifier ReturnType MethodName(Arguments)
{
    // Method Body
}
```

### Example

```java
public static void run()
{
    System.out.println("Running...");
}
```

---

# Parts of a Method

## Method Header

```java
public static void
```

Contains:

* Access Modifier
* Non-Access Modifier
* Return Type

## Method Signature

```java
run()
```

Contains:

* Method Name
* Arguments/Parameters

## Method Body

```java
{
    System.out.println("Running...");
}
```

Contains the actual implementation.

---

# Why Do We Use Methods?

It is not recommended to write business logic directly inside a class.

### Not Recommended

```java
class ATM
{
    System.out.println("Withdraw Money");
}
```

### Recommended

```java
class ATM
{
    public static void withdraw()
    {
        System.out.println("Enter amount to withdraw");
    }
}
```

---

# Access Modifiers

Java provides 4 access modifiers.

| Modifier  | Description                              |
| --------- | ---------------------------------------- |
| public    | Accessible everywhere                    |
| private   | Accessible only within same class        |
| protected | Accessible within package and subclasses |
| default   | Accessible within same package           |

### Examples

```java
public static void run()
{
}
```

```java
private static void run()
{
}
```

```java
protected static void run()
{
}
```

```java
static void run()
{
}
```

(Default Access Modifier)

---

# Non-Access Modifiers

Commonly used:

## Static Method

```java
public static void run()
{
}
```

## Non-Static Method

```java
public void run()
{
}
```

---

# Important Points About Methods

1. Methods are used to perform specific tasks.
2. A method consists of a header, signature, and body.
3. Methods must be declared inside a class.
4. A class can contain any number of methods.
5. A method executes only when called.
6. Methods are called using their signature.
7. A method can be called multiple times.
8. One method cannot be declared inside another method.

---

# Invalid Method Example

```java
public static void fly()
{
    public static void air()
    {
    }
}
```

❌ Invalid

Reason: A method cannot be defined inside another method.

---

# Valid Method Example

```java
public static void fly()
{
    air();
}

public static void air()
{
}
```

✅ Valid

Reason: One method can call another method.

---

# Advantage of Methods

### Reusability

Write once and use many times.

---

# Example 1: Bank Application

```java
public class Bank
{
    public static void main(String[] args)
    {
        System.out.println("Customer Details");

        custDetails();

        System.out.println("------Details Again------");

        custDetails();
    }

    public static void custDetails()
    {
        System.out.println("Acc Name : Pooja");
        System.out.println("Acc No   : 1234567");
        System.out.println("IFSC     : IOS345");
        System.out.println("Bank     : IOB");
        System.out.println("Branch   : Secunderabad");
        System.out.println("Email    : poojaitsme@gmail.com");
        System.out.println("DOB      : 25-07-1993");
        System.out.println("Contact  : 9865472134");
    }
}
```

### Output

```text
Customer Details
Acc Name : Pooja
Acc No   : 1234567
...
------Details Again------
Acc Name : Pooja
Acc No   : 1234567
...
```

Notice that the same method is reused twice.

---

# Example 2: Book Application

```java
public class Book
{
    public static void main(String[] args)
    {
        System.out.println("Book Details");

        bookDetails();
    }

    public static void bookDetails()
    {
        System.out.println("Book Name      : Machine Design");
        System.out.println("Book Pages     : 957");
        System.out.println("Book Author    : Ravikiran");
        System.out.println("Book Publisher : SS.Ratan");
        System.out.println("Book Edition   : 7th Edition");
    }
}
```

---

# Example 3: Car Application

```java
public class Car
{
    public static void main(String[] args)
    {
        System.out.println("Car Details");

        carDetails();
    }

    public static void carDetails()
    {
        System.out.println("Company : Hyundai");
        System.out.println("Model   : i10");
        System.out.println("Color   : Red");
        System.out.println("Year    : 2015");
        System.out.println("Fuel    : Diesel");
        System.out.println("Mileage : 20 Kmpl");
        System.out.println("Top Speed : 165 Kmph");
    }
}
```

---

# Example 4: Facebook Account Details

```java
public class Facebook
{
    public static void main(String[] args)
    {
        System.out.println("Facebook Account Details");

        acntDetails();
    }

    public static void acntDetails()
    {
        System.out.println("Name     : Ravi");
        System.out.println("Phone    : 9573267321");
        System.out.println("Email    : raviitsme@gmail.com");
        System.out.println("DOB      : 25-07-1993");
        System.out.println("Location : Visakhapatnam");
    }
}
```

---

# Interview Questions

### 1. What is a method?

A method is a block of code used to perform a specific task and executes when called.

### 2. What is the main advantage of methods?

**Reusability**.

### 3. Can a method be declared inside another method?

No.

### 4. Can one method call another method?

Yes.

### 5. How many methods can a class contain?

Any number of methods.

### 6. When does a method execute?

Only when it is called.

### 7. What are the four access modifiers?

* public
* private
* protected
* default

### 8. What are static and non-static methods?

* `static` methods belong to the class.
* `non-static` methods belong to objects.

---

# Methods with Arguments

* A method can have any number of inputs in the form of arguments.

### Examples

```java
public static void add()     // Method with zero arguments (0 inputs)

public static void add(int i) // Method with an integer argument
```

### Important Points

* When we call a method with arguments, we must pass values of the required data type.
* While passing arguments, we must ensure they follow the same sequence defined in the method declaration.

### Example Program

```java
public class Login
{
    public static void main(String args[])
    {
        registration("John", "john123@gmail.com", 9845915746L);
    }

    // Static method without specific return type and with arguments
    public static void registration(String name, String email, long contact)
    {
        System.out.println("Name : " + name);
        System.out.println("Email address : " + email);
        System.out.println("Contact details : " + contact);
    }
}
```

---

# Practice Programs

## 1. Facebook Login App

**WAP to create a Facebook Login app with arguments as username and password.**

```java
public class FacebookLogin
{
    public static void main(String args[])
    {
        login("Ravi", "r@Vi3421");
    }

    public static void login(String username, String password)
    {
        System.out.println("username : " + username);
        System.out.println("password : " + password);
    }
}
```

---

## 2. Scholarship Login App

**WAP to create a Scholarship Login app with arguments as hall ticket, date of birth, and semester. Print the details for 3 people.**

```java
public class ScholarshipLogin
{
    public static void main(String args[])
    {
        login("Ravi", "10-08-1993", 7);
        login("Kiran", "25-07-1994", 5);
        login("Prasanna", "16-05-1995", 3);
    }

    public static void login(String hallticket, String DOB, int semester)
    {
        System.out.println("Hallticket : " + hallticket);
        System.out.println("D.O.B : " + DOB);
        System.out.println("Semester : " + semester);
        System.out.println("--------------------");
    }
}
```

---

# Method with Return Type

* If required, we can define a method with a specific return type.

### Syntax

```java
public static int add()
{
    // Perform required operations
    return integerTypeValue;
}
```

### Important Points

* Whenever we define a method with a return type, we are specifying that the method will return a value of that particular type.
* It is mandatory to write a `return` statement in a method with a specific return type.

---

# Return Keyword

### Definition

* Used inside a method whenever we define a method with a specific return type.
* Used to return data and exit from the method.

### Rules

* `return` keyword must be the last executable statement in a method.
* `return` keyword does **not** print data.
* We cannot write more than one executable `return` statement in a method because after executing a return statement, the method exits immediately.

### Interview Question

**Q. What is `void`?**

**A.** `void` means no specific return type, or the method is not returning any value.

---

## Example: Method with Return Type

```java
public class IntRegistration
{
    public static long phnofield()
    {
        return 9874561337L;
    }

    public static char genderfield()
    {
        return 'M';
    }

    public static void main(String args[])
    {
        System.out.println(phnofield());
        System.out.println(genderfield());
    }
}
```

---

# Program 1: QSpiders Login

**Note:** Execution always starts from the `main()` method.

```java
public class QspidersLogin
{
    public static boolean login(String username, int pwd)
    {
        if(username == "Qspiders" && pwd == 5454)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void main(String args[])
    {
        System.out.println(login("Qspiders", 5454));
        System.out.println(login("Qspiders", 5354));
    }
}
```

---

# Program 2: Area of Triangle, Circle, Rectangle, and Square

```java
class Areas
{
    public static float areaOfTri(int breadth, int height)
    {
        float area1 = 0.5f * breadth * height;
        return area1;
    }

    public static float areaOfCircle(int radius)
    {
        float pie = 3.141f;
        float area2 = pie * radius * radius;
        return area2;
    }

    public static int areaOfRect(int breadth, int length)
    {
        int area3 = length * breadth;
        return area3;
    }

    public static int areaOfSquare(int side)
    {
        int area4 = side * side;
        return area4;
    }

    public static void main(String args[])
    {
        System.out.println("Area of Triangle is : " + areaOfTri(5, 10));
        System.out.println("Area of Circle is : " + areaOfCircle(5));
        System.out.println("Area of Rectangle is : " + areaOfRect(3, 8));
        System.out.println("Area of Square is : " + areaOfSquare(4));
    }
}
```

---

# Program 3: Sum of Even Digits

**WAP to print the sum of even digits in a number (logic can be used for any digit number).**

### Example

Number = `1234`

Even digits = `2 + 4 = 6`

```java
public class SumE
{
    public static void main(String args[])
    {
        int num = 1234, sum = 0, rem;

        while(num > 0)
        {
            rem = num % 10;
            num = num / 10;

            if(rem % 2 == 0)
            {
                sum = sum + rem;
            }
        }

        System.out.println("Sum of even numbers is : " + sum);
    }
}
```

---

# Program 4: Leap Year

### Conditions for Leap Year

1. The year should be divisible by `4`.
2. The leap year should not be a century year (`100, 200, 300...`) **or** it should be divisible by `400`.

### Program

```java
public class LeapYear
{
    public static void main(String args[])
    {
        int year = 2020;

        if(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
        {
            System.out.println("Leap Year");
        }
        else
        {
            System.out.println("Not Leap Year");
        }
    }
}
```

---

# TYPES OF VARIABLES

---

Depending on declaration, variables are divided into two types:

1. Local variable
2. Global variable (Data member)

---

## LOCAL VARIABLES

---

Variables which are declared inside the method (main() or userdefine) and within the scope of class ({}), such variables are called as **Local variables**. 

### Are Local variables accessible everywhere?

---

Local variables are accessible only within the method in which they are declared, they are not accessible outside of that method.

* If we try to access them we will get compile time error saying that **"Cannot find Symbol"**.

### Example

```java
//Local variable//

public class Employee
{
    public static void displaydetails()
    {
        String ename="John";
        int eid=8075;
        String desg="Developer";

        System.out.println("Employee name: "+ename);
        System.out.println("Employee is : "+eid);
        System.out.println("Designation : "+desg);
    }

    public static void main(String args[])
    {
        displaydetails();

        String ename="John";
        int eid=8054;
        String desg="QA";

        System.out.println("Employee name: "+ename);
        System.out.println("Employee is : "+eid);
        System.out.println("Designation : "+desg);
    }
}
```

### Output

```text
Employee name: John
Employee is : 8075
Designation : Developer
Employee name: John
Employee is : 8054
Designation : QA
```

---

### Is Initialization of local variable mandatory?

---

Yes, it is mandatory to initialize local variables. If we do not initialize them, we will get compile time error saying that:

**"Variable might not initialized"**

### Example

```java
public class Student
{
    public static void main(String args[])
    {
        String sclname; //local var
        String branch="HYD";

        System.out.println(sclname); //CTE (Compile Time Error)
                                    //because initialization of local variable is mandatory

        displayDetails();
    }

    public static void displayDetails()
    {
        String sclname="S.T.H.C.S"; //local var
        String sname="Pooja";       //lv
        int age=22;                 //lv
        long contact=9587459618l;   //lv
        String email="poojamine@gmail.com"; //lv

        System.out.println(sname+" "+age+" "+contact+" "+email);
        System.out.println(sclname);

        System.out.println(branch); //CTE because branch is local to main()
    }
}
```

---

### Access Modifiers with Local Variables

---

Local variables can only be **default** or **final** i.e. we cannot declare a local variable as **private, public or protected**.

### Example

```java
public static void add()
{
    int i=100;          //local variable declaration is valid

    public int i=100;   //local variable declaration is Invalid

    final int i=100;    //local variable declaration is valid

    private int i=100;  //local variable declaration is Invalid

    protected int i=100;//local variable declaration is Invalid
}
```

---


# GLOBAL VARIABLES (Data Members)

---

Variables which are declared outside the method (main() or userdefine) and within the scope of class ({}), such variables are called as **Global variables**.

Global variables are divided into 2 types:

### 1. Static Variables (also called Class Variables)

```java
static int i=100;
```

### 2. Non-Static Variables (also called Instance Variables)

```java
int i=100;
```

* Global variables are accessible everywhere within the scope of a class.

---

## Example

```java
//Global variables//

class Student1
{
    //static global variables

    static String college="Qspiders"; //global var
    static String stdname="Rohan";    //global var
    static int passingmarks=60;       //global var
    static int maxbacklogs=12;        //global var

    public static void main(String args[])
    {
        System.out.println("Details from main method are:");
        System.out.println(college);
        System.out.println(stdname);
        System.out.println(passingmarks);
        System.out.println(maxbacklogs);

        displayDetails();
    }

    public static void displayDetails()
    {
        System.out.println("Details from display method are:");
        System.out.println("Name : "+stdname);
        System.out.println("College : "+college);
        System.out.println("Marks : "+passingmarks);
        System.out.println("AffordableBacks : "+maxbacklogs);
    }
}
```

### Output

```text
E:\Qspiders Java\programs>java Student1

Details from main method are:
Qspiders
Rohan
60
12

Details from display method are:
Name : Rohan
College : Qspiders
Marks : 60
AffordableBacks : 12
```

---

## Example

```java
public class Bank1
{
    static String custname="Pooja";
    static String bname="IndianOverseas";
    static long accno=2424242143l;
    static String IFSC="IOBA127500";
    static String brname="Hyderabad";
    static String acctype="Savings";

    public static void displayDetails()
    {
        System.out.println("Cust Name : "+custname);
        System.out.println("Accno : "+accno);
        System.out.println("IFSC : "+IFSC);
        System.out.println("Branch name : "+brname);
        System.out.println("Account type : "+acctype);
    }

    public static void main(String args[])
    {
        System.out.println("---Details of Customer From---"+bname);
        displayDetails();
    }
}
```

### Output

```text
E:\Qspiders Java\programs>java Bank1

---Details of Customer From---IndianOverseas
Cust Name : Pooja
Accno : 2424242143
IFSC : IOBA127500
Branch name : Hyderabad
Account type : Savings
```

---

## Example

```java
public class Bank2
{
    static String custname="Pooja";
    static String bname="IndianOverseas";
    static long accno=2424242143l;
    static String IFSC="IOBA127500";
    static String brname="Hyderabad";
    static String acctype="Savings";

    public static void displayDetails()
    {
        String custname="Rohan"; //local var
        long accno=2465656553l;  //local var

        System.out.println("Cust Name : "+custname); //Rohan
        System.out.println("Accno : "+accno);        //2465656553
        System.out.println("IFSC : "+IFSC);          //IOBA127500
        System.out.println("Branch name : "+brname);//Hyderabad
        System.out.println("Account type : "+acctype);//Savings
    }

    public static void main(String args[])
    {
        String bname="ICICI"; //local var

        System.out.println("---Details of Customer From---"+bname);//ICICI

        displayDetails();
    }
}
```

### Output

```text
E:\Qspiders Java\programs>java Bank2

---Details of Customer From---ICICI
Cust Name : Rohan
Accno : 2465656553
IFSC : IOBA127500
Branch name : Hyderabad
Account type : Savings
```

---

### Q. What if local variable and global variable names are same?

**A.** If local variable and global variable names are same, first priority is always given to local variables because they are inside the method and nearer for execution to JVM.

### Example

```java
class STDApp
{
    //static global variables//

    static String name="Qspiders";
    static long contact=9573267325l;
    static String email="Qspiders@gmal.com";

    public static void enquiry()
    {
        //local vars

        String name="QspidersKPHB";
        String email="Qspiderskphb@gmail.com";

        //print statements

        System.out.println(name);
        System.out.println(contact);
        System.out.println(email);
    }

    public static void main(String args[])
    {
        enquiry();
    }
}
```

### Output

```text
E:\Qspiders Java\programs>java STDApp

QspidersKPHB
9573267325
Qspiderskphb@gmail.com
```

---

### Q. Is initialization of global variable mandatory?

**A.** It is not mandatory to initialize global variables. If we do not initialize them, JVM will take default values based on data types.

| Data Type              | Default Value |
| ---------------------- | ------------- |
| byte, short, int, long | 0             |
| float, double          | 0.0           |
| String                 | null          |
| boolean                | false         |
| char                   | empty space   |

---

## Program

```java
class Demo
{
    //static global variables without initialization//

    static int i;
    static long lt;
    static short s;
    static byte b;
    static char ch;
    static boolean b1;
    static float f;
    static double d;
    static String str;

    public static void main(String args[])
    {
        System.out.println(i);
        System.out.println(lt);
        System.out.println(s);
        System.out.println(b);
        System.out.println(ch);
        System.out.println(b1);
        System.out.println(f);
        System.out.println(d);
        System.out.println(str);
    }
}
```

### Output

```text
E:\Qspiders Java\programs>java Demo

0
0
0
0

false
0.0
0.0
null
```

---

### Important Points

* Global variables can be **public, default, private, protected and final**.
* Global variables are also called **Data Members**.

---

# DIFFERENCES BETWEEN LOCAL VARIABLE AND GLOBAL VARIABLE

| Local Variable                                                                                                                    | Global Variable                                                                                          |
| --------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| Variables which are declared inside the method and within the scope of class.                                                     | Variables which are declared outside the method and within the scope of class.                           |
| Local variables are accessible only within the method in which they are declared, they are not accessible outside of that method. | Global variables are accessible everywhere within the scope of a class.                                  |
| It is mandatory to initialize local variables, if we do not initialize them we will get compile time error.                       | It is not mandatory to initialize. If not initialized, JVM will take default values based on data types. |
| There are no types for local variables.                                                                                           | Global variables are divided into 2 types i.e. static and non-static.                                    |
| Local variables can be default or final.                                                                                          | Global variables can be public, private, protected, default or final.                                    |
| Local variables are present in Stack area.                                                                                        | Global variables are present in Heap area.                                                               |

# Method Overloading



---

## Definition

The process of developing multiple methods with the **same name** but **different argument lists** is called **Method Overloading**.

---

## Rules for Defining Argument List

### 1. Number of arguments must be different
```
swiggy()
swiggy(String name)
swiggy(String name, int orderid)
```

### 2. Types (datatype) of arguments must be different
```
add(int i, int j)
add(double d, double d1)
add(String s1, String s2)
```

### 3. Sequence or position of arguments must be different
```
login(String ul, int id)
login(int id, String pwd)
```

> We go for method overloading when we want to **perform one task in multiple ways**.

---

## Real-World Examples

### a) Travelling (Hyd → Bang)
- Bus
- Train
- Aeroplane
- Bike
- Car
- ...etc

### b) Keeping Phone Password
- Number lock
- Drawing pattern
- Finger print
- Face print
- Face sensor

### c) Payment in any E-Commerce
- Credit
- Debit
- Wallets (GPay, PhonePe, AmazonPay, Paytm)
- Cash on Delivery

### d) Banking
- Online banking
- Physical banking
- ATM banking
- App banking

---

## Code Example 1 — Basic Method Overloading



```java
class Mover {
    public static void main(String args[]) {
        add();
        add(100, 200);
        add('A', 'B');   // A=65, B=66
        add(100, "java");
        add("SQL", 200);
    }

    public static void add() {
        System.out.println(10 + 20);
    }

    public static void add(int i, int j) {
        System.out.println(i + j);
    }

    public static void add(char ch1, char ch2) {
        System.out.println(ch1 + ch2);
    }

    public static void add(int i, String s) {
        System.out.println(i + s);
    }

    public static void add(String s, int i) {
        System.out.println(s + i);
    }
}
```

**Output:**
```
C:\Users\Ravi Kiran\Qspiders Java\programs>java Mover
30
300
131
100java
SQL200
```

---

## Code Example 2 — Payment Module using Method Overloading

**WAP to develop the APP for Payment Module using Method Overloading.**

```java
class Payment {

    public static void payment(String wallettype, int UID) {
        System.out.println("WalletType : " + wallettype);
        System.out.println("UID : " + UID);
    }

    public static void payment(String cardtype, long cardno, int cvvnumber) {
        System.out.println("CardType : " + cardtype);
        System.out.println("CardNo : " + cardno);
        System.out.println("CvvNumber : " + cvvnumber);
    }

    public static void payment(String type, String username, int pwd, long Accountnumber) {
        System.out.println("Type : " + type);
        System.out.println("Username : " + username);
        System.out.println("Pwd : " + pwd);
        System.out.println("AccountNumber : " + Accountnumber);
    }

    public static void main(String args[]) {
        payment("Gpay", 9573);
        payment("Debitcard", 4581643278941254l, 522);
        payment("savings", "ravikiran", 6547523, 35486785214l);
    }
}
```

---

## FAQs on Method Overloading

### Q. Can we overload `main()` or not?

**A.** Yes, we can overload `main()` because JVM only knows a `main()` which has arguments as `String args[]` (command line arguments). So if we define any other methods whose name is `main`, they will be considered as user-defined methods.

> **Note:** In practical/real-time, it is suggested **not** to overload `main()`.

```java
class Mover1 {
    public static void main(String args[]) {
        main();
        main(100, 200);
        main('A', 'B');   // A=65, B=66
        main(100, "java");
        main("SQL", 200);
    }

    public static void main() {
        System.out.println(10 + 20);
    }

    public static void main(int i, int j) {
        System.out.println(i + j);
    }

    public static void main(char ch1, char ch2) {
        System.out.println(ch1 + ch2);
    }

    public static void main(int i, String s) {
        System.out.println(i + s);
    }

    public static void main(String s, int i) {
        System.out.println(s + i);
    }
}
```

**Output:**
```
C:\Users\Ravi Kiran\Qspiders Java\programs>java Mover1
30
300
131
100java
SQL200
```

---

### Q. Can we overload `final` methods?

**A.** Yes, we can overload `final` methods because the `final` keyword says **do not change implementation**, and in overloading we are **not changing implementation** — rather we are changing arguments.

---

### Q. Can we overload `private` methods or not?

**A.** Yes, we can overload `private` methods because private methods are accessible everywhere in the **same class**, and overloading also happens within the class.

---

### Q. Can we overload non-static methods or not?

**A.** Yes, we can overload non-static methods, but to call them we have to create an **Object**.

#### Non-static Methods

- If we do **not** mention the `static` keyword in a method, it will become non-static.
  - Example: `public void register() {}`

- If we want to call non-static methods from:
  1. A **static method** → we have to create an object.
  2. A **non-static method** → no need to create an object; we can access directly.

---

## Syntax for Object Creation

```java
classname referencevar = new classname();
// OR
classname referencevar = new constructor();
```

- `new` is a keyword which is responsible to create an object.
- With the `new` keyword, one object gets created and it loads all non-static members (non-static methods and non-static variables) and returns the reference address.
- Now using the reference address and dot (`.`) operator, we can access all non-static methods and variables present inside the object.

---

## Conclusion Points

| #  | Rule |
|----|------|
| 1  | `static` variables can be accessed directly in static methods. |
| 2  | `static` variables can be accessed directly in non-static methods. |
| 3  | Non-static variables **cannot** be accessed directly in static methods. |
| 4  | Non-static variables can be accessed directly in non-static methods. |
| 5  | `static` method can be called directly from static methods. |
| 6  | `static` method can be called directly from non-static methods. |
| 7  | Non-static method **cannot** be called directly from static methods. |
| 8  | Non-static method can be called directly from non-static methods. |

---

## Example — CarA Class

Demonstrates accessing static variables, non-static variables, and calling a non-static method via an object created inside a static method.

```java
public class CarA
{
    static String brname = "Audi";  // static variable
    int capacity = 4;               // non-static variable

    // non-static method
    public void details()
    {
        String color = "Black";      // local variable
        long price = 5500000l;       // local variable
        System.out.println("Car name : "       + brname);
        System.out.println("Car capacity : "   + capacity);
        System.out.println("Color is : "       + color);
        System.out.println("Starting Price : " + price);
    }

    // static method
    public static void features()
    {
        // classname referencevar = new classname();
        CarA c1 = new CarA();   // create object inside static method
        c1.details();           // access non-static method via reference
        System.out.println(c1.capacity);
    }

    public static void main(String args[])
    {
        features();
    }
}
```

**Output:**
```
C:\Users\Ravi Kiran\Qspiders Java\programs>java CarA
Car name : Audi
Car capacity : 4
Color is : Black
Starting Price : 5500000
4
```

---

## Practice — Book1 Class

Demonstrates object scope, calling non-static methods from static and non-static contexts, and re-creating objects in different scopes.

```java
public class Book1
{
    static String bname      = "java";
    int    bpages            = 578;
    String bpublisher        = "PoojaPrintingPress";
    static String bauthor    = "John.RJ";
    static String bcolor     = "Black";

    public static void bookDetails()
    {
        System.out.println("Name : " + bname);
        Book1 b1 = new Book1();  // here b1 is local to bookDetails()
        System.out.println("Pages : "        + b1.bpages);
        System.out.println("Published by : " + b1.bpublisher);
        System.out.println("Author is : "    + bauthor);
        System.out.println("Color is : "     + bcolor);
    }

    public void shopkeeper()
    {
        String sname    = "Ramu Anna";  // local variable
        String saddress = "kingKoti beside busstop opposite to Bahubali tea stall";  // local variable
        System.out.println("I have Purchased from : " + sname);
        System.out.println("Address : "               + saddress);
        bookDetails();  // calling static method from non-static method
    }

    public static void main(String args[])
    {
        // b1.shopkeeper(); // CTE because b1 is local to bookDetails()
        System.out.println("---------Love Story of Book------");
        // here we can again create object as b1 also,
        // because every time we create an object it will be local to its scope
        Book1 b2 = new Book1();
        b2.shopkeeper();
    }
}
```

**Output:**
```
C:\Users\Ravi Kiran\Qspiders Java\programs>java Book1
---------Love Story of Book------
I have Purchased from : Ramu Anna
Address : kingKoti beside busstop opposite to Bahubali tea stall
Name : java
Pages : 578
Published by : PoojaPrintingPress
Author is : John.RJ
Color is : Black
```

> **Note on Object Scope:**
> - `b1` created inside `bookDetails()` is local — it cannot be accessed outside that method.
> - Every time an object is created, it is scoped to where it was defined.
> - `b2` is created fresh in `main()` and is used to call `shopkeeper()`.
> - Static methods can be called directly from non-static methods (no object needed).


# Java Execution Process

---

## How Java Executes a Program

When any Java program runs, the JVM creates two memory blocks and orchestrates a precise sequence of events.

```
┌─────────────────────────────────────────────────────────────┐
│                        JVM RUNTIME                          │
│                                                             │
│   ┌──────────────────────┐    ┌───────────────────────────┐ │
│   │     STACK AREA       │    │        HEAP AREA          │ │
│   │   (Execution Area)   │    │      (Storage Area)       │ │
│   │                      │    │                           │ │
│   │  ┌────────────────┐  │    │  ┌─────────────────────┐  │ │
│   │  │  Class Loader  │  │    │  │  Static Pool Area   │  │ │
│   │  │  (temporary)   │  │    │  │       (SPA)         │  │ │
│   │  └────────────────┘  │    │  │  - static variables │  │ │
│   │          ↓           │    │  │  - static methods   │  │ │
│   │  ┌────────────────┐  │    │  └─────────────────────┘  │ │
│   │  │  main() frame  │  │    │                           │ │
│   │  │                │  │    │  ┌─────────────────────┐  │ │
│   │  │  local vars    │  │    │  │      Object 1       │  │ │
│   │  │  reference     │──┼────┼─▶│  - non-static vars  │  │ │
│   │  │  variables     │  │    │  │  - non-static meths │  │ │
│   │  └────────────────┘  │    │  └─────────────────────┘  │ │
│   │          ↓           │    │                           │ │
│   │  [Garbage Collector] │    │                           │ │
│   └──────────────────────┘    └───────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

---

## Step-by-Step Execution Flow

```
  JVM Starts
      │
      ▼
┌─────────────────┐
│  Class Loader   │  ← Enters stack area
│  loads SPA      │    Loads all static members into Static Pool Area (Heap)
└────────┬────────┘
         │ Job done → exits stack
         ▼
┌─────────────────┐
│   main() called │  ← JVM calls main method
│  enters stack   │    main() frame created in stack under method area
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│  main() runs    │  ← Execution begins
│                 │    Objects created in Heap as needed
│  new ClassName()│ ──────────────────────────────────────▶ [Object in Heap]
└────────┬────────┘
         │ Execution complete
         ▼
┌─────────────────┐
│  main() exits   │  ← Pops off the stack
│  stack          │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Garbage         │  ← JVM calls GC before exiting
│ Collector runs  │    Cleans up entire memory for next execution
└────────┬────────┘
         │
         ▼
      JVM exits
```

### Execution Steps in Detail

1. JVM enters the **stack area** by making a call to the **Class Loader**.
2. Class Loader loads all **static members** into the **Static Pool Area (SPA)** — which is a part of the Heap.
3. Class Loader's job is done; it exits the stack.
4. JVM makes a call to **`main()`**.
5. Upon any method call, the method is loaded into the stack under its **method frame**.
6. Execution of `main()` begins.
7. Once execution is complete, `main()` exits the stack.
8. JVM calls the **Garbage Collector** to clean up memory before exiting.

---

## Memory Layout at a Glance

| Memory Area | Location | What lives there |
|---|---|---|
| **Stack (Execution Area)** | Stack | Method frames, local variables, reference variables |
| **Static Pool Area (SPA)** | Heap | Static variables, static methods |
| **Object** | Heap | Non-static variables, non-static methods |

```
STACK                          HEAP
─────────────────────          ──────────────────────────────────
│ main() frame        │        │  Static Pool Area (SPA)        │
│  - local var x      │        │   static int count = 0         │
│  - ref var obj ─────┼───────▶│   static void display() {...}  │
│                     │        │                                 │
│ methodA() frame     │        │  Object (created by new)        │
│  - local var y      │        │   int id = 5                   │
│                     │        │   void show() {...}             │
─────────────────────          ──────────────────────────────────
  Destroyed when                 Stays in Heap until GC cleans
  method exits                   it up
```

---

## Conclusion Points

### 1. Why static members work without an object

Static methods and static variables are loaded into **SPA before execution starts**. They are ready before `main()` even runs — that's why they are accessible without creating an object.

### 2. Why non-static members need an object

Non-static members are **not loaded before execution**. The `new` keyword creates an object in the Heap and loads all non-static variables and methods into it. Without an object, there is nowhere for them to live.

### 3. Why non-static variables are directly accessible inside non-static methods

Non-static variables and non-static methods are **loaded together inside the same object**. Since they share the same memory area, a non-static method can access non-static variables directly — no object reference needed inside the method itself.

### 4. Static members = single copy

Static members are loaded **only once** in the SPA. Every part of the program shares that same copy — that is what "single copy" means.

### 5. Non-static members = multiple copies

Each time `new` is used, a **new object is created** with its own set of non-static variables and methods. That is what "multiple copies" means.

### 6. Local vs Global variables — where they live

| Variable type | Lives in | Scope |
|---|---|---|
| **Local variable** | Stack (inside method frame) | Within the method only |
| **Global / non-static variable** | Heap (inside object) | As long as the object lives |
| **Static variable** | Heap (SPA) | Entire program lifetime |

### 7–9. Variable locations summary

```
static variables    → SPA (Heap)
non-static vars     → Object (Heap)
local variables     → Stack (inside method frame)
reference variables → Stack (local variables that hold a Heap address)
```

### 10. Why local variable scope is limited to its method

When a method finishes executing, its **frame is popped off the stack**. All local variables inside that frame are destroyed along with it. This is the exact reason why local variables only exist within their method — they literally cease to exist once the method exits.

---

## Quick Reference — Access Rules

| What you want to access | From a static method | From a non-static method |
|---|---|---|
| Static variable | ✅ Directly | ✅ Directly |
| Non-static variable | ❌ Need an object | ✅ Directly |
| Static method | ✅ Directly | ✅ Directly |
| Non-static method | ❌ Need an object | ✅ Directly |

> **Key insight:** If you are in a static context, you must create an object (`new ClassName()`) to access anything non-static. If you are already inside a non-static method, you can access everything directly because you are already running inside an object.
