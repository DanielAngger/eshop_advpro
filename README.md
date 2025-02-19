# eshop_advpro

## Link -> https://alright-carlen-danielangger-00867105.koyeb.app

## Tutorial 1
### Reflection 1
Saya sudah memperlajari cara menulis kode dengan prinsip "clean code" dan "secure code practices" minggu ini, dan saya rasa saya sudah berusaha untuk menerapkannya pada tutorial kali ini, saya sudah bertanya kepada teman saya tentang kode saya, dan mereka rata-rata langsung mengetahui dan bisa mempelajari maksud kode saya dan apa yang coba saya lakukan, jadi secara garis besar, saya rasa saya sudah menerapkan apa yang saya pelajari minggu ini, tidak hanya kebenaran sintaks dan fungsionalnya saja.

### Reflection 2
1. Setelah menulis berbagai unit test, saya merasa kagum terhadap seorang tester, karena seorang tester harus bisa berpikir kreatif untuk membuat test yang bisa mencakup semua fungsional dari sebuah program. Untuk seberapa banyak unit test yang harus kita buat, sebuah unit test harus bisa mencakup semua fungsional yang ada pada program, jadi seberapa banyaknya tergantung skala program yang ditest. Dan ini juga berkaitan dengan verifikasi program kita, untuk program kita bisa terverifikasi, kita perlu membuat unit test sebanyak fungsional prorgam yang harus ditest. Dan bila pun kita memiliki 100% kode yang tercover. Karena itu hanya menunjukkan sebuah skala yang bisa dihitung, bukan kebenaran suatu program. Pasti banyak edge-case yang harus dipikirkan juga.
2. Bila kita membuat sebuah kelas baru yang kelas sebelumnya sebagai acuan, kita perlu memikirkan kemungkinan redundansi, atau kode yang tumpang tindih atau berulang. Bila memang kode itu sudah ada sebelumnya dan kita ingin menggunakannya lagi,kita bisa menggunakan konsep inheritance atau implement.

## Tutorial 2
### Reflection
1. Saya sudah menyelesaikan salah satu masalah saat melakukan scanning code, yaitu "Unnecessary modifier 'public'" pada ProductService.java saya, khususnya pada abstract method findAll(). Strategi saya adalah memahami masalah yang disajikan pada report yang ada, kemudian saya diarahkan pada dokumentasi yang ada, kemudian saya mengikuti dokumentasi itu dan menyelesaikan masalahnya. Terbukti, masalah tersebut tidak muncul lagi di commit berikutnya.
2. Ya, saya sudah mengimplementasikan CI/CD pada workflows saya. Pertama, saya sudah membuat file ci.yml yang berfungsi untuk menjalankan test dan menguji kode saya, yang kemudian disajikan dalam html file yang berisi laporan hasil test oleh JaCoCo. Lalu, untuk CD, memang tidak eksplisit terdapat di workflows saya, tetapi saya menggunakan PaaS Koyeb, dimana Koyeb memiliki fitur auto-deploy yang bisa diintegrasikan langsung ke repositori GitHub, jadi setiap saya melakukan push ke master, itu sudah mengimplementasikan CD.