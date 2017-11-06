# Procesare de imagini
* Segmentare prn metoda Otsu - prin impartirea histogramei
* Egalizarea histogramei
    * operatie integrala(unitara) `nxm` - 1
    * interpolare ca la statistica
    * netezirea histogramei

Se pot ua diferite feature-uri specifice pe context sau generale. Generale:
* Se poate lucra cu gradient isi hist lor
* gradient aproximati local cu o convolutie(ex. Sobel, Robert Cross etc)
* HOG = histogram of gradients

Se pot si la nivel de textura:
* "texteli" - grupuri de pixeli
* aboradri bazate pe gramatici(ca la compilatoare) de texteli*

Metode bazate pe contururi si forme. **Ex** Implementare SOBEL
