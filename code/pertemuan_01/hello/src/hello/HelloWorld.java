	package hello;
	
	import java.util.Scanner; // Mengimpor kelas Scanner
	
	public class HelloWorld {
		// Metode utama: Titik masuk program
		public static void main(String[] args) {
			// Deklarasi variabel
			String name;
			
			// Membuat objek Scanner untuk menerima input dari pengguna
			Scanner scanner = new Scanner(System.in);
			
			// Meminta input dari pengguna
			System.out.print("Masukkan nama Anda: ");
			name = scanner.nextLine();  // Membaca input nama dari pengguna
			
			// Menampilkan output dengan input pengguna
			System.out.println("Hello " + name + "!");
			
			// Menutup objek Scanner
			scanner.close();
		}
	}