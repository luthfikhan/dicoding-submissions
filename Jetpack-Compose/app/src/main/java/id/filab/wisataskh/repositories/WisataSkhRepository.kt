package id.filab.wisataskh.repositories

import id.filab.wisataskh.model.WisataSkhModel

class WisataSkhRepository {
    companion object {
        val wisataSkhModels = listOf(
            WisataSkhModel(
                id = 1,
                title = "Royal Water Adventure",
                description = "Royal Water Adventure adalah tempat wisata di Kabupaten Sukoharjo, Provinsi Jawa Tengah yang menawarkan keseruan wahana kolam renang, lengkap dengan beragam fasilitas pendukungnya.\n" +
                        "\n" +
                        "Royal Water Adventure adalah salah-satu waterpark di Sukoharjo yang sangat populer, dan sering dipadati oleh para pengunjung, terutama saat liburan tiba.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/fasilitas-di-royal-water-adventure.jpg"
            ),
            WisataSkhModel(
                id = 2,
                title = "Curug Krajan",
                description = "Curug Krajan adalah wisata Sukoharjo yang masih tergolong sebagai objek wisata yang masih perawan, alias belum terdapat sentuhan kreasi tangan manusia.\n" +
                        "\n" +
                        "Curug Krajan Sukoharjo memiliki pesona air terjun yang didominasi oleh bebatuan, serta beberapa undakan. Air yang turun tidak terlalu deras, dan terurai layaknya tirai yang indah.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/curug-krajan.jpg"
            ),
            WisataSkhModel(
                id = 3,
                title = "Gunung Sepikul",
                description = "Gunung Sepikul adalah salah-satu gunung yang ada di Sukoharjo yang cocok dijadikan tujuan pendakian untuk para pemula. Hanya saja, wisata Sukoharjo tersebut belum cocok untuk anak-anak usia SD.\n" +
                        "\n" +
                        "Gunung Sepikul tidak hanya menyimpan pesona keindahan alam saja. Gunung tersebut memiliki sisi legenda yang masih menjadi cerita di beberapa kalangan masyarakat.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/sejarah-gunung-sepikul.jpg"
            ),
            WisataSkhModel(
                id = 4,
                title = "Gunung Pegat",
                description = "Gunung Pegat adalah wisata Bulu yang menyajikan pesona alam pegunungan, yang dilengkapi dengan beragam spot selfie yang instagramable.\n" +
                        "\n" +
                        "Gunung Pegat juga memiliki pesona lain berupa sisi misteri, atau mitos yang masih berkembang hingga saat ini. Dan seiring perkembangan jaman, sisi tersebut malah menjadi daya tarik tersendiri untuk para pengunjung.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/spot-foto-3.jpg"
            ),
            WisataSkhModel(
                id = 5,
                title = "Ronce Stable",
                description = "Ronce Stable menyajikan konten wisata seputar dunia “perkudaan”. Dari mulai menunggangi kuda mengelilingi area pedesaan, hingga kursus menunggangi kuda.\n" +
                        "\n" +
                        "Perlu diketahui mengapa wisata seperti itu tergolong sebagai konsep wisata anti mainstream, karena konsep seperti itu masih jarang ditemui di daerah – daerah",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/berkuda-di-ronce-stable.jpg"
            ),
            WisataSkhModel(
                id = 6,
                title = "Gunung Taruwongso",
                description = "Daya tariknya terletak dari sajian view alam di puncaknya yang instagramable, serta keberadaan Batu Mancen yang diyakini sebagai batu anti jomblo.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/puncak-gunung-taruwongso.jpg"
            ),
            WisataSkhModel(
                id = 7,
                title = "Pesanggrahan Langenharjo",
                description = "Pesanggrahan Langenharjo, adalah wisata Sukoharjo yang menyimpan nilai sejarah yang sangat tinggi. Hal Tersebut dikarenakan keberadaanya sangat terkait erat dengan sejarah Keraton Kasultanan Surakarta.\n" +
                        "\n" +
                        "Saat ini Pesanggrahan Langenharjo memiliki status sebagai Cagar Budaya Situs Pesanggrahan Langenharjo. Dengan status tersebut diharapkan mampu menjaga segala sesuatu yang memiliki nilai sejarah yang tinggi di area Pesanggrahan Langenharjo.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/pesanggrahan-langenharjo.jpg"
            ),
            WisataSkhModel(
                id = 8,
                title = "Sendang Semurup",
                description = "Sendang Semurup adalah wisata Sukoharjo yang menawarkan konsep wisata alam, khususnya tempat mancing yang ditata secara baik, dan mampu menyesuaikan dengan kebutuhan trend wisata kekinian.\n" +
                        "\n" +
                        "Sendang Semurup bisa menjadi alternatif pilihan tujuan wisata di Kabupaten Sukoharjo terutama saat liburan tiba. Karena di kawasan tersebut terdapat banyak spot selfie yang instagramable, serta wahana wisata lainnya.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/sendang-semurup-sukoharjo.jpg"
            ),
            WisataSkhModel(
                id = 9,
                title = "Wisata Alam Batu Seribu",
                description = "Daya tarik yang pertama dari Wisata Alam Batu Seribu Sukoharjo adalah spot kolam renangnya. Kolam renang tersebut menyajikan sensasi yang tidak biasa.\n" +
                        "\n" +
                        "Karena lokasinya berada di atas gunung, serta dikelilingi pepohonan dengan nuansa hutan. Selain itu, airnya bersumber dari mata air langsung yang ada di kawasan tersebut.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/fasilitas-di-batu-seribu.jpg"
            ),
            WisataSkhModel(
                id = 10,
                title = "Taman Pakujoyo",
                description = "Taman Pakujoyo adalah salah-satu taman di Sukoharjo yang sangat populer, inspiratif, dan bisa dibilang sebagai taman yang memiliki aturan antimainstream.\n" +
                        "\n" +
                        "Taman Pakujoyo memiliki aturan yang menjadi pro, dan kontra khususnya di kalangan milenial. Yaitu bagi siapa saja yang kepergok berduaan, atau mesum akan langsung diceburkan ke danau yang ada di sana.",
                image = "https://cdn.nativeindonesia.com/foto/2022/06/taman-lalu-lintas-pakujoyo.jpg"
            )
        )
    }

    fun getWisataSkhById(id: Int): WisataSkhModel {
        return wisataSkhModels.find { it.id == id } ?: wisataSkhModels[0]
    }
}