# 📘 Java String Programs – Question List (1–50)

### 🔹 Basic Level

1. Reverse a string
2. Check whether a string is a palindrome
3. Find the length of a string without using `length()`
4. Count vowels and consonants in a string
5. Compare two strings without using `equals()`
6. Check whether two strings are anagrams
7. Count the occurrence of a given character in a string
8. Find the first non-repeated character in a string
9. Find the first repeated character in a string
10. Remove all white spaces from a string

---

### 🔹 Intermediate Level

11. Count the number of words in a string
12. Reverse each word in a string
13. Reverse the order of words in a sentence
14. Find duplicate characters in a string
15. Remove duplicate characters from a string
16. Find the longest word in a string
17. Check whether a string contains only digits
18. Print all possible substrings of a string
19. Check whether one string is a rotation of another
20. Check whether a string is a pangram

---

### 🔹 Advanced / Logic-Based

21. Compress a string (e.g., `aaabb → a3b2`)
22. Find the second most frequent character in a string
23. Find the longest common prefix among a set of strings
24. Find the longest common substring between two strings
25. Check whether parentheses in a string are balanced

---

### 🔹 Coding Test / Tricky Programs

26. Swap two strings without using a third variable
27. Find missing alphabet characters from a string
28. Convert a numeric string into an integer (without parsing methods)
29. Convert an integer into a string (without using inbuilt methods)
30. Remove special characters from a string

---

### 🔹 Frequently Asked Interview Questions

31. Count the frequency of each character in a string
32. Print all unique characters in a string
33. Print all duplicate characters in a string
34. Check whether a substring exists within a string (without `contains()`)
35. Find the longest substring without repeating characters
36. Find the longest repeating substring
37. Count vowels in each word of a string
38. Capitalize the first letter of each word in a string
39. Toggle the case of each character in a string
40. Count uppercase letters, lowercase letters, digits, and special characters

---

### 🔹 Real-World & Edge Case Programs

41. Remove a specific character from a string
42. Insert a character at a given position in a string
43. Check whether a string follows a given pattern (e.g., `abba`)
44. Find the minimum number of characters required to make a string palindrome
45. Validate an email address using string logic
46. Reverse a string using recursion
47. Compare two strings ignoring case
48. Find the second longest word in a string
49. Check whether a string contains only alphabet characters
50. Count the number of consonants in a string

---

# 🔥 JAVA STRING PROGRAMS (1–50)

---

## 1️⃣ Reverse a String

```java
class P1 {
 public static void main(String[] args) {
  String s="Java", r="";
  for(int i=s.length()-1;i>=0;i--) r+=s.charAt(i);
  System.out.println(r);
 }
}
```

---

## 2️⃣ Palindrome

```java
class P2 {
 public static void main(String[] args) {
  String s="madam"; boolean ok=true;
  int i=0,j=s.length()-1;
  while(i<j){
   if(s.charAt(i)!=s.charAt(j)){ ok=false; break; }
   i++; j--;
  }
  System.out.println(ok);
 }
}
```

---

## 3️⃣ Length without length()

```java
class P3 {
 public static void main(String[] args) {
  String s="Java"; int c=0;
  for(char x:s.toCharArray()) c++;
  System.out.println(c);
 }
}
```

---

## 4️⃣ Count vowels & consonants

```java
class P4 {
 public static void main(String[] args) {
  String s="hello"; int v=0,c=0;
  for(int i=0;i<s.length();i++){
   char ch=s.charAt(i);
   if(ch=='a'||ch=='e'||ch=='i'||ch=='o'||ch=='u') v++;
   else if(ch>='a'&&ch<='z') c++;
  }
  System.out.println(v+" "+c);
 }
}
```

---

## 5️⃣ Compare strings (NO equals)

```java
class P5 {
 public static void main(String[] args) {
  String a="Java",b="Java"; boolean same=true;
  if(a.length()!=b.length()) same=false;
  else{
   for(int i=0;i<a.length();i++)
    if(a.charAt(i)!=b.charAt(i)){same=false;break;}
  }
  System.out.println(same);
 }
}
```

---

## 6️⃣ Anagram (NO sort)

```java
class P6 {
 public static void main(String[] args) {
  String a="listen",b="silent";
  int[] f=new int[256];
  for(int i=0;i<a.length();i++) f[a.charAt(i)]++;
  for(int i=0;i<b.length();i++) f[b.charAt(i)]--;
  boolean ok=true;
  for(int x:f) if(x!=0){ok=false;break;}
  System.out.println(ok);
 }
}
```

---

## 7️⃣ Count character occurrence

```java
class P7 {
 public static void main(String[] args) {
  String s="hello"; char t='l'; int c=0;
  for(int i=0;i<s.length();i++)
   if(s.charAt(i)==t) c++;
  System.out.println(c);
 }
}
```

---

## 8️⃣ First non-repeated character

```java
class P8 {
 public static void main(String[] args) {
  String s="swiss";
  for(int i=0;i<s.length();i++){
   boolean u=true;
   for(int j=0;j<s.length();j++)
    if(i!=j && s.charAt(i)==s.charAt(j)){u=false;break;}
   if(u){System.out.println(s.charAt(i));break;}
  }
 }
}
```

---

## 9️⃣ First repeated character

```java
class P9 {
 public static void main(String[] args) {
  String s="swiss";
  for(int i=0;i<s.length();i++)
   for(int j=i+1;j<s.length();j++)
    if(s.charAt(i)==s.charAt(j)){
     System.out.println(s.charAt(i)); return;
    }
 }
}
```

---

## 🔟 Remove whitespaces

```java
class P10 {
 public static void main(String[] args) {
  String s="Java World",r="";
  for(int i=0;i<s.length();i++)
   if(s.charAt(i)!=' ') r+=s.charAt(i);
  System.out.println(r);
 }
}
```

---

## 1️⃣1️⃣ Count words

```java
class P11 {
 public static void main(String[] args) {
  String s="Java is easy"; int c=1;
  for(int i=0;i<s.length();i++)
   if(s.charAt(i)==' ') c++;
  System.out.println(c);
 }
}
```

---

## 1️⃣2️⃣ Reverse each word

```java
class P12 {
 public static void main(String[] args) {
  String s="Java is easy",w="",r="";
  for(int i=0;i<=s.length();i++){
   if(i==s.length()||s.charAt(i)==' '){
    for(int j=w.length()-1;j>=0;j--) r+=w.charAt(j);
    r+=" "; w="";
   }else w+=s.charAt(i);
  }
  System.out.println(r);
 }
}
```

---

## 1️⃣3️⃣ Reverse words order

```java
class P13 {
 public static void main(String[] args) {
  String s="Java is easy",r="",w="";
  for(int i=s.length()-1;i>=0;i--){
   if(s.charAt(i)==' '){ r+=" "+w; w=""; }
   else w=s.charAt(i)+w;
  }
  System.out.println(w+r);
 }
}
```

---

## 1️⃣4️⃣ Duplicate characters

```java
class P14 {
 public static void main(String[] args) {
  String s="programming";
  for(int i=0;i<s.length();i++){
   for(int j=i+1;j<s.length();j++)
    if(s.charAt(i)==s.charAt(j)){
     System.out.println(s.charAt(i)); break;
    }
  }
 }
}
```

---

## 1️⃣5️⃣ Remove duplicate characters

```java
class P15 {
 public static void main(String[] args) {
  String s="programming",r="";
  for(int i=0;i<s.length();i++){
   boolean seen=false;
   for(int j=0;j<r.length();j++)
    if(s.charAt(i)==r.charAt(j)){seen=true;break;}
   if(!seen) r+=s.charAt(i);
  }
  System.out.println(r);
 }
}
```

---

## 1️⃣6️⃣ Longest word

```java
class P16 {
 public static void main(String[] args) {
  String s="Java is very easy",w="",lw="";
  for(int i=0;i<=s.length();i++){
   if(i==s.length()||s.charAt(i)==' '){
    if(w.length()>lw.length()) lw=w;
    w="";
   }else w+=s.charAt(i);
  }
  System.out.println(lw);
 }
}
```

---

## 1️⃣7️⃣ Only digits

```java
class P17 {
 public static void main(String[] args) {
  String s="12345"; boolean ok=true;
  for(int i=0;i<s.length();i++)
   if(s.charAt(i)<'0'||s.charAt(i)>'9'){ok=false;break;}
  System.out.println(ok);
 }
}
```

---

## 1️⃣8️⃣ All substrings

```java
class P18 {
 public static void main(String[] args) {
  String s="abc";
  for(int i=0;i<s.length();i++)
   for(int j=i+1;j<=s.length();j++)
    System.out.println(s.substring(i,j));
 }
}
```

---

## 1️⃣9️⃣ String rotation

```java
class P19 {
 public static void main(String[] args) {
  String a="ABCD",b="CDAB",t=a+a; boolean f=false;
  for(int i=0;i<=t.length()-b.length();i++){
   int j=0;
   while(j<b.length() && t.charAt(i+j)==b.charAt(j)) j++;
   if(j==b.length()){f=true;break;}
  }
  System.out.println(f);
 }
}
```

---

## 2️⃣0️⃣ Pangram

```java
class P20 {
 public static void main(String[] args) {
  String s="abcdefghijklmnopqrstuvwxyz"; boolean ok=true;
  for(char c='a';c<='z';c++){
   boolean f=false;
   for(int i=0;i<s.length();i++)
    if(s.charAt(i)==c){f=true;break;}
   if(!f){ok=false;break;}
  }
  System.out.println(ok);
 }
}
```

---

## 2️⃣1️⃣ String compression

```java
class P21 {
 public static void main(String[] args) {
  String s="aaabb",r=""; int c=1;
  for(int i=0;i<s.length();i++){
   if(i+1<s.length()&&s.charAt(i)==s.charAt(i+1)) c++;
   else{ r+=s.charAt(i)+""+c; c=1; }
  }
  System.out.println(r);
 }
}
```

---

## 2️⃣2️⃣ Second most frequent char

```java
class P22 {
 public static void main(String[] args) {
  String s="aabbbc";
  int[] f=new int[256];
  for(int i=0;i<s.length();i++) f[s.charAt(i)]++;
  int max=0,sec=0; char ch=0;
  for(int i=0;i<256;i++){
   if(f[i]>max){sec=max;max=f[i];ch=(char)i;}
  }
  System.out.println(ch);
 }
}
```

---

## 2️⃣3️⃣ Longest common prefix

```java
class P23 {
 public static void main(String[] args) {
  String[] a={"flower","flow","flight"};
  String r="";
  for(int i=0;i<a[0].length();i++){
   char c=a[0].charAt(i); boolean ok=true;
   for(int j=1;j<a.length;j++)
    if(i>=a[j].length()||a[j].charAt(i)!=c){ok=false;break;}
   if(ok) r+=c; else break;
  }
  System.out.println(r);
 }
}
```

---

## 2️⃣4️⃣ Longest common substring

```java
class P24 {
 public static void main(String[] args) {
  String a="abcdef",b="zcdemf",r="";
  for(int i=0;i<a.length();i++)
   for(int j=0;j<b.length();j++){
    int x=i,y=j; String t="";
    while(x<a.length()&&y<b.length()&&a.charAt(x)==b.charAt(y)){
     t+=a.charAt(x); x++; y++;
    }
    if(t.length()>r.length()) r=t;
   }
  System.out.println(r);
 }
}
```

---

## 2️⃣5️⃣ Balanced parentheses

```java
class P25 {
 public static void main(String[] args) {
  String s="()()"; int c=0;
  for(int i=0;i<s.length();i++){
   if(s.charAt(i)=='(') c++;
   else c--;
   if(c<0){System.out.println(false);return;}
  }
  System.out.println(c==0);
 }
}
```

---

## 2️⃣6️⃣ Swap strings (no temp)

```java
class P26 {
 public static void main(String[] args) {
  String a="Hello",b="World";
  a=a+b; b=a.substring(0,a.length()-b.length());
  a=a.substring(b.length());
  System.out.println(a+" "+b);
 }
}
```

---

## 2️⃣7️⃣ Missing alphabet chars

```java
class P27 {
 public static void main(String[] args) {
  String s="abc";
  for(char c='a';c<='z';c++){
   boolean f=false;
   for(int i=0;i<s.length();i++)
    if(s.charAt(i)==c){f=true;break;}
   if(!f) System.out.print(c+" ");
  }
 }
}
```

---

## 2️⃣8️⃣ String to integer

```java
class P28 {
 public static void main(String[] args) {
  String s="123"; int n=0;
  for(int i=0;i<s.length();i++)
   n=n*10+(s.charAt(i)-'0');
  System.out.println(n);
 }
}
```

---

## 2️⃣9️⃣ Integer to string

```java
class P29 {
 public static void main(String[] args) {
  int n=123; String s="";
  while(n>0){ s=(n%10)+s; n/=10; }
  System.out.println(s);
 }
}
```

---

## 3️⃣0️⃣ Remove special characters

```java
class P30 {
 public static void main(String[] args) {
  String s="Ja@va#1",r="";
  for(int i=0;i<s.length();i++){
   char c=s.charAt(i);
   if((c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c>='0'&&c<='9'))
    r+=c;
  }
  System.out.println(r);
 }
}
```

---

You’re right 👍 — thanks for catching that.
Below are the **REMAINING 20 programs (31–50)**, **READY-TO-RUN**, **logic-only**, **NO inbuilt String methods** (no `equals`, `split`, `contains`, `replace`, etc.).

Now you’ll have the **full 50/50 set** ✅

---

# 🔥 REMAINING JAVA STRING PROGRAMS (31–50)

---

## 3️⃣1️⃣ Count frequency of each character

```java
class P31 {
 public static void main(String[] args) {
  String s="hello";
  int[] f=new int[256];

  for(int i=0;i<s.length();i++)
   f[s.charAt(i)]++;

  for(int i=0;i<256;i++)
   if(f[i]>0)
    System.out.println((char)i+" -> "+f[i]);
 }
}
```

---

## 3️⃣2️⃣ Print unique characters

```java
class P32 {
 public static void main(String[] args) {
  String s="program";
  for(int i=0;i<s.length();i++){
   boolean unique=true;
   for(int j=0;j<s.length();j++)
    if(i!=j && s.charAt(i)==s.charAt(j)){unique=false;break;}
   if(unique) System.out.print(s.charAt(i)+" ");
  }
 }
}
```

---

## 3️⃣3️⃣ Print duplicate characters

```java
class P33 {
 public static void main(String[] args) {
  String s="programming";
  int[] f=new int[256];

  for(int i=0;i<s.length();i++)
   f[s.charAt(i)]++;

  for(int i=0;i<256;i++)
   if(f[i]>1)
    System.out.print((char)i+" ");
 }
}
```

---

## 3️⃣4️⃣ Check substring (NO contains)

```java
class P34 {
 public static void main(String[] args) {
  String s="JavaProgramming", sub="Program";
  boolean found=false;

  for(int i=0;i<=s.length()-sub.length();i++){
   int j=0;
   while(j<sub.length() && s.charAt(i+j)==sub.charAt(j))
    j++;
   if(j==sub.length()){found=true;break;}
  }
  System.out.println(found);
 }
}
```

---

## 3️⃣5️⃣ Longest non-repeating substring

```java
class P35 {
 public static void main(String[] args) {
  String s="abcabcbb", res="";
  for(int i=0;i<s.length();i++){
   String temp="";
   for(int j=i;j<s.length();j++){
    boolean dup=false;
    for(int k=0;k<temp.length();k++)
     if(temp.charAt(k)==s.charAt(j)){dup=true;break;}
    if(dup) break;
    temp+=s.charAt(j);
   }
   if(temp.length()>res.length()) res=temp;
  }
  System.out.println(res);
 }
}
```

---

## 3️⃣6️⃣ Longest repeating substring

```java
class P36 {
 public static void main(String[] args) {
  String s="banana", res="";
  for(int i=0;i<s.length();i++)
   for(int j=i+1;j<s.length();j++){
    int x=i,y=j; String temp="";
    while(y<s.length() && s.charAt(x)==s.charAt(y)){
     temp+=s.charAt(x); x++; y++;
    }
    if(temp.length()>res.length()) res=temp;
   }
  System.out.println(res);
 }
}
```

---

## 3️⃣7️⃣ Count vowels in each word

```java
class P37 {
 public static void main(String[] args) {
  String s="Java is easy", w="";
  for(int i=0;i<=s.length();i++){
   if(i==s.length()||s.charAt(i)==' '){
    int c=0;
    for(int j=0;j<w.length();j++){
     char ch=w.charAt(j);
     if(ch=='a'||ch=='e'||ch=='i'||ch=='o'||ch=='u') c++;
    }
    System.out.println(w+" -> "+c);
    w="";
   }else w+=s.charAt(i);
  }
 }
}
```

---

## 3️⃣8️⃣ Capitalize first letter of each word

```java
class P38 {
 public static void main(String[] args) {
  String s="java is easy", r="";
  boolean cap=true;

  for(int i=0;i<s.length();i++){
   char ch=s.charAt(i);
   if(ch==' '){ cap=true; r+=ch; }
   else if(cap && ch>='a'&&ch<='z'){
    r+=(char)(ch-32); cap=false;
   } else {
    r+=ch; cap=false;
   }
  }
  System.out.println(r);
 }
}
```

---

## 3️⃣9️⃣ Toggle case

```java
class P39 {
 public static void main(String[] args) {
  String s="JaVa", r="";
  for(int i=0;i<s.length();i++){
   char c=s.charAt(i);
   if(c>='a'&&c<='z') r+=(char)(c-32);
   else if(c>='A'&&c<='Z') r+=(char)(c+32);
  }
  System.out.println(r);
 }
}
```

---

## 4️⃣0️⃣ Count uppercase, lowercase, digits, special chars

```java
class P40 {
 public static void main(String[] args) {
  String s="JaVa@123";
  int u=0,l=0,d=0,sp=0;

  for(int i=0;i<s.length();i++){
   char c=s.charAt(i);
   if(c>='A'&&c<='Z') u++;
   else if(c>='a'&&c<='z') l++;
   else if(c>='0'&&c<='9') d++;
   else sp++;
  }
  System.out.println(u+" "+l+" "+d+" "+sp);
 }
}
```

---

## 4️⃣1️⃣ Remove a specific character

```java
class P41 {
 public static void main(String[] args) {
  String s="banana", r=""; char rem='a';
  for(int i=0;i<s.length();i++)
   if(s.charAt(i)!=rem) r+=s.charAt(i);
  System.out.println(r);
 }
}
```

---

## 4️⃣2️⃣ Insert character at position

```java
class P42 {
 public static void main(String[] args) {
  String s="Java", r=""; char ch='X'; int pos=2;
  for(int i=0;i<s.length();i++){
   if(i==pos) r+=ch;
   r+=s.charAt(i);
  }
  System.out.println(r);
 }
}
```

---

## 4️⃣3️⃣ Check pattern (abba)

```java
class P43 {
 public static void main(String[] args) {
  String p="abba";
  String[] w={"dog","cat","cat","dog"};
  boolean ok=true;

  for(int i=0;i<p.length();i++)
   for(int j=i+1;j<p.length();j++)
    if(p.charAt(i)==p.charAt(j) && w[i]!=w[j]) ok=false;

  System.out.println(ok);
 }
}
```

---

## 4️⃣4️⃣ Minimum chars to make palindrome

```java
class P44 {
 public static void main(String[] args) {
  String s="abc"; int c=0;
  int i=0,j=s.length()-1;

  while(i<j){
   if(s.charAt(i)!=s.charAt(j)){ c++; i++; }
   else{ i++; j--; }
  }
  System.out.println(c);
 }
}
```

---

## 4️⃣5️⃣ Check valid email (basic)

```java
class P45 {
 public static void main(String[] args) {
  String s="test@gmail.com";
  int at=0,dot=0;

  for(int i=0;i<s.length();i++){
   if(s.charAt(i)=='@') at++;
   if(s.charAt(i)=='.') dot++;
  }
  System.out.println(at==1 && dot>=1);
 }
}
```

---

## 4️⃣6️⃣ Reverse string using recursion

```java
class P46 {
 static void rev(String s,int i){
  if(i<0) return;
  System.out.print(s.charAt(i));
  rev(s,i-1);
 }
 public static void main(String[] args) {
  rev("Java",3);
 }
}
```

---

## 4️⃣7️⃣ Check string equality ignoring case

```java
class P47 {
 public static void main(String[] args) {
  String a="Java",b="java"; boolean ok=true;
  if(a.length()!=b.length()) ok=false;
  else{
   for(int i=0;i<a.length();i++){
    char x=a.charAt(i),y=b.charAt(i);
    if(x>=65&&x<=90) x+=32;
    if(y>=65&&y<=90) y+=32;
    if(x!=y){ok=false;break;}
   }
  }
  System.out.println(ok);
 }
}
```

---

## 4️⃣8️⃣ Find second longest word

```java
class P48 {
 public static void main(String[] args) {
  String s="Java is very easy";
  String w="",l1="",l2="";

  for(int i=0;i<=s.length();i++){
   if(i==s.length()||s.charAt(i)==' '){
    if(w.length()>l1.length()){
     l2=l1; l1=w;
    }else if(w.length()>l2.length()){
     l2=w;
    }
    w="";
   }else w+=s.charAt(i);
  }
  System.out.println(l2);
 }
}
```

---

## 4️⃣9️⃣ Check string has only alphabets

```java
class P49 {
 public static void main(String[] args) {
  String s="Java"; boolean ok=true;
  for(int i=0;i<s.length();i++){
   char c=s.charAt(i);
   if(!(c>='a'&&c<='z'||c>='A'&&c<='Z')){
    ok=false; break;
   }
  }
  System.out.println(ok);
 }
}
```

---

## 5️⃣0️⃣ Count consonants

```java
class P50 {
 public static void main(String[] args) {
  String s="hello"; int c=0;
  for(int i=0;i<s.length();i++){
   char ch=s.charAt(i);
   if(ch>='a'&&ch<='z' &&
      !(ch=='a'||ch=='e'||ch=='i'||ch=='o'||ch=='u'))
    c++;
  }
  System.out.println(c);
 }
}
```

---

## ✅ DONE — FULL **50 / 50 STRING PROGRAMS**

If you want next:

- 📘 **Single PDF (ALL 50, logic-only)**
- 🧠 **Dry-run explanation per program**
- 🎯 **Top 20 most-asked interview picks**
- 🔥 **Java + TypeScript comparison**

Just say 👍
