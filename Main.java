import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Menu> daftarMenu = new ArrayList<>();
    private static ArrayList<Menu> pesanan = new ArrayList<>();
    private static ArrayList<Integer> kuantitasPesanan = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMenu();

        boolean running = true;
        while (running) {
            System.out.println("\n==== Aplikasi Restoran Sederhana ====");
            System.out.println("1. Menu Pelanggan (Pemesanan)");
            System.out.println("2. Menu Pengelola (Manajemen Menu)");
            System.out.println("3. Keluar");
            System.out.print("Pilihan: ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    menuPelanggan();
                    break;
                case "2":
                    menuPengelola();
                    break;
                case "3":
                    running = false;
                    System.out.println("Terima kasih telah menggunakan aplikasi.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }
        scanner.close();
    }

    private static void initializeMenu() {
        daftarMenu.add(new Menu("Ayam Goreng", 15000.0, "Makanan"));
        daftarMenu.add(new Menu("Ayam Bakar", 20000.0, "Makanan"));
        daftarMenu.add(new Menu("Nasi Goreng", 15000.0, "Makanan"));
        daftarMenu.add(new Menu("Nasi Uduk", 15000.0, "Makanan"));
        daftarMenu.add(new Menu("Nasi Padang", 15000.0, "Makanan"));
        daftarMenu.add(new Menu("Nasi Ayam", 15000.0, "Makanan"));
        daftarMenu.add(new Menu("Es Kopi", 5000.0, "Minuman"));
        daftarMenu.add(new Menu("Es Teh Manis", 5000.0, "Minuman"));
        daftarMenu.add(new Menu("Es Jeruk", 7000.0, "Minuman"));
        daftarMenu.add(new Menu("Es Lemon Tea", 8000.0, "Minuman"));
        daftarMenu.add(new Menu("Es Susu", 6000.0, "Minuman"));
    }

    private static void menuPelanggan() {
        pesanan.clear();
        kuantitasPesanan.clear();
        System.out.println("\n==== Menu Pelanggan ====");
        System.out.println("Masukkan nomor menu, atau ketik 'selesai' untuk mengakhiri pemesanan.");

        boolean memesan = true;
        while (memesan) {
            tampilkanDaftarMenuDenganNomor();

            System.out.print("Masukkan nomor menu atau 'selesai': ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                memesan = false;
            } else {
                try {
                    int nomorMenu = Integer.parseInt(input);
                    Menu menuDitemukan = cariMenuBerdasarkanNomor(nomorMenu);

                    if (menuDitemukan != null) {
                        int kuantitas = getValidIntInput("Masukkan kuantitas (minimal 1): ");
                        pesanan.add(menuDitemukan);
                        kuantitasPesanan.add(kuantitas);
                        System.out.println(kuantitas + "x '" + menuDitemukan.getNama() + "' berhasil ditambahkan ke pesanan!");
                    } else {
                        System.out.println("Nomor menu '" + nomorMenu + "' tidak valid.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Harap masukkan nomor menu atau ketik 'selesai'.");
                }
            }
        }
        
        if (!pesanan.isEmpty()) {
            hitungDanCetakStruk();
        } else {
            System.out.println("Tidak ada pesanan. Kembali ke menu utama.");
        }
    }

    private static void menuPengelola() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n==== Menu Pengelola (Manajemen Menu) ====");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Tampilkan Daftar Menu");
            System.out.println("5. Kembali");
            System.out.print("Pilihan: ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tambahMenu();
                    break;
                case "2":
                    ubahHargaMenu();
                    break;
                case "3":
                    hapusMenu();
                    break;
                case "4":
                    tampilkanDaftarMenuDenganNomor();
                    break;
                case "5":
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }
    }

    private static void tampilkanDaftarMenuDenganNomor() {
        System.out.println("\n==== Daftar Menu Lengkap ====");
        if (daftarMenu.isEmpty()) {
            System.out.println("Daftar menu kosong.");
            return;
        }
        for (int i = 0; i < daftarMenu.size(); i++) {
            daftarMenu.get(i).displayInfo(i + 1);
        }
        System.out.println("----------------------------------------");
    }

    private static Menu cariMenu(String nama) {
        for (Menu menu : daftarMenu) {
            if (menu.getNama().trim().equalsIgnoreCase(nama.trim())) {
                return menu;
            }
        }
        return null;
    }

    private static Menu cariMenuBerdasarkanNomor(int nomor) {
        if (nomor > 0 && nomor <= daftarMenu.size()) {
            return daftarMenu.get(nomor - 1);
        }
        return null;
    }

    private static void hitungDanCetakStruk() {
        double totalBiaya = 0;
        int jumlahMinuman = 0;
        Menu minumanTermahal = null;

        System.out.println("\n\n==== Struk Pembayaran ====");
        System.out.println("----------------------------------------");
        System.out.println("Item yang dipesan:");
        System.out.printf("%-5s%-25s%10s%12s%n", "Qty", "Nama Menu", "Harga", "Subtotal");
        System.out.println("----------------------------------------");

        for (int i = 0; i < pesanan.size(); i++) {
            Menu menu = pesanan.get(i);
            int kuantitas = kuantitasPesanan.get(i);
            double subtotal = menu.getHarga() * kuantitas;
            totalBiaya += subtotal;

            System.out.printf("%-5d%-25sRp %,-8.0fRp %,-10.0f%n", 
                            kuantitas, 
                            menu.getNama(), 
                            menu.getHarga(),
                            subtotal);

            if (menu.getKategori().equalsIgnoreCase("Minuman")) {
                jumlahMinuman += kuantitas;
                
                if (minumanTermahal == null || menu.getHarga() > minumanTermahal.getHarga()) {
                    minumanTermahal = menu;
                }
            }
        }
        
        System.out.println("----------------------------------------");
        
        double pajak = totalBiaya * 0.1;
        double biayaPelayanan = 20000;
        double totalSebelumDiskon = totalBiaya + pajak + biayaPelayanan;
        double totalDiskon = 0;
        boolean diskon10PersenDiterapkan = false;
        boolean promoBeliSatuGratisSatuDiterapkan = false;
        double diskon10PersenValue = 0;
        double potonganMinumanValue = 0;

        if (totalSebelumDiskon > 100000) {
            diskon10PersenValue = totalSebelumDiskon * 0.1;
            totalDiskon += diskon10PersenValue;
            diskon10PersenDiterapkan = true;
        }

        if (totalBiaya > 50000 && jumlahMinuman >= 2 && minumanTermahal != null) {
            potonganMinumanValue = minumanTermahal.getHarga();
            totalDiskon += potonganMinumanValue;
            promoBeliSatuGratisSatuDiterapkan = true;
        }

        double totalAkhir = totalSebelumDiskon - totalDiskon;

        System.out.printf("SubTotal: %30sRp %,-10.0f%n", "", totalBiaya);
        System.out.printf("Pajak (10%%): %27sRp %,-10.0f%n", "", pajak);
        System.out.printf("Biaya Pelayanan: %23sRp %,-10.0f%n", "", biayaPelayanan);
        System.out.printf("Total Sebelum Diskon: %18sRp %,-10.0f%n", "", totalSebelumDiskon);

        System.out.println("\n--- Potongan / Promo Diterapkan ---");

        if (diskon10PersenDiterapkan) {
            System.out.printf("Diskon (10%% >Rp 100K): %21s-Rp %,-10.0f%n", "", diskon10PersenValue);
        }

        if (promoBeliSatuGratisSatuDiterapkan) {
            System.out.printf("Promo Beli 1 Gratis 1 Minuman (%s): %2s-Rp %,-10.0f%n", 
                minumanTermahal.getNama(), "", potonganMinumanValue);
        }
        
        if (!diskon10PersenDiterapkan && !promoBeliSatuGratisSatuDiterapkan) {
            System.out.println("Tidak ada diskon yang diterapkan.");
        }
        
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL AKHIR: %27sRp %,-10.0f%n", "", totalAkhir);
        System.out.println("----------------------------------------");
        System.out.println("Terima kasih telah berkunjung!");
    }

    private static void tambahMenu() {
        System.out.println("\n==== Tambah Menu ====");
        System.out.print("Nama Menu: ");
        String nama = scanner.nextLine();
        
        if (cariMenu(nama) != null) {
            System.out.println("Menu dengan nama '" + nama + "' sudah ada.");
            return;
        }
        
        double harga = getValidDoubleInput("Harga Menu (Angka): ");

        String kategori = "";
        boolean validKategori = false;
        while (!validKategori) {
            System.out.print("Kategori Menu (Makanan/Minuman): ");
            kategori = scanner.nextLine();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                validKategori = true;
            } else {
                System.out.println("Kategori menu harus 'Makanan' atau 'Minuman'. Silakan ulangi.");
            }
        }

        daftarMenu.add(new Menu(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan.");
    }

    private static void ubahHargaMenu() {
        System.out.println("\n==== Ubah Harga Menu ====");
        if (daftarMenu.isEmpty()) {
            System.out.println("Daftar menu kosong. Tidak ada yang bisa diubah.");
            return;
        }
        tampilkanDaftarMenuDenganNomor();

        int nomor = getValidIntInput("Nomor menu yang ingin diubah: ");

        if (nomor < 1 || nomor > daftarMenu.size()) {
            System.out.println("Nomor menu tidak valid.");
            return;
        }

        Menu menu = daftarMenu.get(nomor - 1);
        System.out.println("Menu yang dipilih: " + menu.getNama() + " - Rp " + menu.getHarga());

        double hargaBaru = getValidDoubleInput("Masukkan harga baru (Angka): ");
        menu.setHarga(hargaBaru);
        System.out.println("Harga menu berhasil diubah.");
    }

    private static void hapusMenu() {
        System.out.println("\n==== Hapus Menu ====");
        if (daftarMenu.isEmpty()) {
            System.out.println("Daftar menu kosong. Tidak ada yang bisa dihapus.");
            return;
        }
        tampilkanDaftarMenuDenganNomor();
        
        int nomor = getValidIntInput("Nomor menu yang ingin dihapus: ");
        
        if (nomor < 1 || nomor > daftarMenu.size()) {
            System.out.println("Nomor menu tidak valid.");
        } else {
            Menu menu = daftarMenu.get(nomor - 1);
            System.out.println("Menu yang dipilih: " + menu.getNama() + " - Rp " + menu.getHarga());
            
            System.out.print("Apakah anda yakin ingin menghapus menu ini? (y/n): ");
            String konfirmasi = scanner.nextLine();
            
            if (konfirmasi.equalsIgnoreCase("y")) {
                daftarMenu.remove(nomor - 1);
                System.out.println("Menu berhasil dihapus.");
            } else {
                System.out.println("Penghapusan menu dibatalkan.");
            }
        }
    }

    private static int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= 1) {
                    return value;
                } else {
                    System.out.println("Input harus angka bulat positif (minimal 1).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka bulat.");
            }
        }
    }

    private static double getValidDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Harga harus positif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka yang benar.");
            }
        }
    }
}