# Sensorado

Moderná aplikácia na identifikáciu a testovanie senzorov smartfónu.

### Využitie

- diagnostika nefungujúcich, nesprávne fungujúcich a nevhodne nakalibrovaných senzorov
  
- overenie dostupnosti senzorov v zariadení a zistenie informácií o nich
  
- možnosť otestovania a pozorovania aktuálnych dát senzorov
  
- pomoc vývojárom iných aplikácií využívajúcich dané senzory
  

### Návrh

- aplikácia automaticky deteguje dostupné senzory v zariadení
  
- po vybratí senzoru zobrazí jeho aktuálne dáta v reálnom čase
  
- zobrazuje aj ďalšie informácie: výrobca senzoru, citlivosť, odozva, maximálna hodnota, spotreba,...
  
- každý podporovaný zdokumentovaný senzor má definované vlastné jednotky (nevypisuje dáta v "neznámych" veličinách)
  

### Predbežný návrh dizajnu

![](/dokumentacia/zdroje/sensorado-estimated-design-blueprint.png)

### Podobné aplikácie

![](/dokumentacia/zdroje/google-play-store-sensor-test-light.png#gh-light-mode-only)

![](/dokumentacia/zdroje/google-play-store-sensor-test-dark.png#gh-dark-mode-only)

![](/dokumentacia/zdroje/google-play-store-sensors-multitool-light.png#gh-light-mode-only)

![](/dokumentacia/zdroje/google-play-store-sensors-multitool-dark.png#gh-dark-mode-only)

![](/dokumentacia/zdroje/google-play-store-sensor-test-toolbox-light.png#gh-light-mode-only)

![](/dokumentacia/zdroje/google-play-store-sensor-test-toolbox-dark.png#gh-dark-mode-only)

### Chýbajúce a nevhodné funkcie

- nepotrebné "hry"
  
- nevhodné rozloženie
  
- chýbajúce informácie o kamerových senzoroch
  
- chýbajúce typy modernejších senzorov (krokomer,...)
  
- zastaralý dizajn
  
- zlá používateľská skúsenosť
  
- problémy s implementáciou na Huawei zariadeniach bez Google služieb
  

### Implementácia

Aplikácia je naprogramovaná v jazyku Kotlin, s použitím moderných technológií ako napríklad Jetpack Compose a Material Design 3. Minimálne SDK sme kvôli spomínaným technológiám nastavili na najnižšiu možnú verziu, ktorou je verzia 21, všetky ostatné potrebné kontroly podporovaných funkcií pod jednotlivými verziami SDK prebiehajú počas behu aplikácie.

Aplikácia sa dynamicky prispôsobuje svetlému či tmavému režimu nastavenému v nastaveniach systému a podporuje dynamické farby v systéme Android 12 a novšom. Pri otočení telefónu si aplikácia uchováva svoj stav, pričom na miestach, kde je to potrebné, aj upravuje rozloženie na základe veľkosti, resp. orientácie obrazovky.

Všetky textové zdroje sú uložené v samostatnom súbore zdrojov, odkiaľ sú načítavané. Pri potrebných textoch sú takisto vytvorené aj textové zdroje, ktoré na základe definovanej číselnej hodnoty prispôsobujú svoj text. Toto však neplatí o textoch automaticky generovaných a spracovávaných zo zdrojov operačného systému Android.

Základná navigácia v aplikácii je riešená pomocou spodného panela na obrazovke. Pomocou nej sa používateľ môže prepínať medzi tromi sekciami: súhrnným zobrazením, zoznamom kamier a zoznamom senzorov.

Súhrnné zobrazenie ukazuje celkový počet senzorov a následne aj rozdelený počet do kategórií: kamerové senzory a iné senzory.

Zoznam kamier dynamicky generuje zoznam priamo zo systému. Obsahuje ako logické, tak aj fyzické kamery (na podporovaných systémoch). Ikony zobrazené pri kamerách odrážajú konkrétny typ kamery: predná, zadná alebo externá. Po vybratí konkrétnej kamery aplikácia vypíše všetky možnosti, ktoré systém ponúka, vývojári si tak podľa týchto informácií môžu viac dohľadať v dokumentácii systému Android. Aplikácia pôvodne zobrazovala aj konkrétne pohľady kamier s možnosťou úpravy niektorých nastavení, avšak po odozve od vývojárov, pre ktorých je táto aplikácia primárne určená, bola táto funkcionalita odstránená, nakoľko bola rušivá a pre účel aplikácie nebola veľmi prínosná, keďže takúto funkciu už väčšinou plní aj aplikácia fotoaparátu/kamery nainštalovaná do zariadenia výrobcom.

Zoznam senzorov rovnako dynamicky generuje celý zoznam priamo zo systému, vždy teda zobrazí iba senzory, ktoré sú v zariadení reálne dostupné, vrátane senzorov doplnených výrobcom zariadenia nad rámec senzorov v dokumentácii systému Android. Ikony senzorov uvedených v dokumentácii sa na základe typu senzoru upravia na ikony odrážajúce daný typ. Po vybratí konkrétneho senzoru aplikácia spustí dynamické vypisovanie hodnôt vyčítavaných zo senzora v reálnom čase (ak senzor takéto dáta ponúka, v opačnom prípade vypíše používateľovi informáciu, že žiadne dáta nie sú dostupné a na základe toho upraví aj ikonu dát), spolu so všetkými dostupnými informáciami o senzore.

Kvôli zobrazovaniu najaktuálnejších dát bola implementovaná najvyššia rýchlosť aktualizácie dát, spolu s možnosťou pozastavenia ich vykresľovania. Pre spomínanú vysokú rýchlosť aktualizácie bolo však potrebné (od API 31) aplikácii prideliť nové povolenie:

```xml
<uses-permission
    android:name="android.permission.HIGH_SAMPLING_RATE_SENSORS"
    tools:ignore="HighSamplingRate" />
```

Pre určité senzory, ako napríklad krokomer, bolo potrebné registrovať použitie funkcií a pridať povolenie pre sledovanie fyzickej aktivity (aplikácia si ho však vypýta, až keď bude používateľ chcieť túto funkciu využiť):

```xml
<uses-feature
    android:name="android.hardware.sensor.stepcounter"
    android:required="false" />
<uses-feature
    android:name="android.hardware.sensor.stepdetector"
    android:required="false" />
<uses-permission
    android:name="android.permission.ACTIVITY_RECOGNITION" />
```

### Súčasný dizajn

![](/dokumentacia/zdroje/sensorado-design-dark.png)

![](/dokumentacia/zdroje/sensorado-design-light.png)

### Zdroje

![](/dokumentacia/zdroje/sources.png)

### Informácie o aplikácii

| ![](/dokumentacia/zdroje/sensorado-logo.png) | Názov balíčka: `sk.uniza.fri.sensorado`<br/>Súčasná verzia: `0.0.181-rc2` |
|----------------------------------------------|---------------------------------------------------------------------------|
