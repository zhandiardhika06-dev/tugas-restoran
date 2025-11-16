public class Menu {
    private String nama;
    private double harga;
    private String kategori;

    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // getter dan setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void displayInfo(int nomor) {
        System.out.printf("%d. %-20s (Rp %,-10.0f)[%s]%n", nomor, nama, harga, kategori);
    }
    public void displayInfo() {
        System.out.printf("%-20s (Rp %,-10.0f)%n", nama, harga);
    }
}
