# Labirent Oyunu Simülasyonu

Bu proje, ajanların bir labirent içinde hareket ettiği ve hedeflerine ulaşmaya çalıştığı bir simülasyon oyunudur.

## Özellikler

- Dinamik labirent oluşturma
- Çoklu ajan desteği
- Dönen koridorlar
- Güç artırıcılar ve tuzaklar
- Grafiksel kullanıcı arayüzü
- Detaylı oyun istatistikleri

## Oyun Mekanikleri

### Labirent
- Rastgele oluşturulan labirent yapısı
- Duvarlar, tuzaklar ve güç artırıcılar
- Belirli satırların dönme özelliği

### Ajanlar
- Her ajanın benzersiz kimlik numarası
- Konum ve hareket geçmişi takibi
- Güç artırıcı kullanabilme yeteneği
- Hedefe ulaşma durumu kontrolü

### Oyun Akışı
1. Oyun başlangıcında labirent boyutları ve ajan sayısı belirlenir
2. Her turda bir ajan hareket eder
3. Ajanlar sırayla hareket eder
4. Güç artırıcılar bir kez kullanılabilir
5. Tuzaklara düşen ajanlar geri adım atar
6. Oyun tüm ajanlar hedefe ulaştığında veya maksimum tur sayısına ulaşıldığında biter

## Kurulum

1. Projeyi klonlayın:
```bash
git clone [proje-url]
```

2. Projeyi derleyin:
```bash
javac src/*.java
```

3. Oyunu çalıştırın:
```bash
java -cp src Main
```

## Kullanım

1. Oyun başladığında aşağıdaki parametreleri girin:
   - Labirent genişliği (5-20)
   - Labirent yüksekliği (5-20)
   - Ajan sayısı (1-5)
   - Tuzak sıklığı (1-10)
   - Güç artırıcı sıklığı (1-10)
   - Maksimum tur sayısı (50-500)

2. Grafiksel arayüz üzerinden:
   - "Start" butonu ile oyunu başlatın
   - "Pause" butonu ile oyunu duraklatın
   - Sağ panelden tur sayısı ve kalan ajan sayısını takip edin

## Sınıf Yapısı

- `Main`: Programın giriş noktası
- `GameController`: Oyun mantığını yönetir
- `GameGUI`: Grafiksel kullanıcı arayüzünü oluşturur
- `MazeManager`: Labirent yapısını ve dönüşümlerini yönetir
- `Agent`: Ajan davranışlarını ve özelliklerini tanımlar
- `TurnManager`: Tur sistemini ve ajan sırasını yönetir
- `CircularLinkedList`: Dairesel bağlı liste veri yapısı
- `Stack`: Yığın veri yapısı

## Geliştirici

- Hasan Akgün
- Hasan Kılınç
