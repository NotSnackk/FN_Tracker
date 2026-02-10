# Fortnite Tracker — instrukcja „kopiuj-wklej” (prosto)

Poniżej masz **dokładnie gdzie wkleić kod** i co ustawić, żeby aplikacja działała stabilnie.

---

## 1) Ustaw klucz API (ważne)

### Plik: `local.properties` (w głównym folderze projektu)
Dopisz na końcu:

```properties
FORTNITE_API_KEY=TU_WKLEJ_SWÓJ_KLUCZ_Z_fortniteapi.io
```

> Bez tego aplikacja pokaże komunikat: „Brak klucza API...”.

---

## 2) Podmień pliki 1:1 (cała zawartość)

Skopiuj i wklej **całe pliki**:

1. `app/build.gradle.kts`
2. `app/src/main/AndroidManifest.xml`
3. `app/src/main/java/com/example/fortnite_tracker/ApiClient.kt`
4. `app/src/main/java/com/example/fortnite_tracker/FortniteApiService.kt`
5. `app/src/main/java/com/example/fortnite_tracker/PlayerStatsResponse.kt`
6. `app/src/main/java/com/example/fortnite_tracker/StatsViewModel.kt`
7. `app/src/main/java/com/example/fortnite_tracker/MainActivity.kt`
8. `app/src/main/java/com/example/fortnite_tracker/SearchActivity.kt`
9. `app/src/main/res/layout/main_activity.xml`

---

## 3) Co już jest zrobione (~90%)

- Ekran startowy → ekran wyszukiwania → ekran statystyk.
- Pobieranie `account_id` po nicku i potem statystyk z API.
- Obsługa trybów: **SOLO / DUO / SQUAD**.
- Loading (`ProgressBar`) i retry przy błędzie.
- Czytelne komunikaty błędów po polsku.
- Internet permission w manifeście.
- Klucz API przeniesiony do `local.properties` (bez trzymania w kodzie).

---

## 4) Ostatnie 10% (co dodać dalej)

1. **Ulubieni gracze** (Room + lista).  
2. **Historia wyszukiwania**.  
3. **Lepsze błędy API** (429 / 404 / 500 osobno).  
4. **Testy ViewModel** (loading/success/error).  
5. **Cache offline** ostatnich statystyk.  
6. **Polish UI** (dark mode, ikony trybów, lepsze spacing).  

---

## 5) Szybka checklista uruchomienia

1. Wklej `FORTNITE_API_KEY` do `local.properties`.  
2. Sync Gradle.  
3. Uruchom aplikację na emulatorze/telefonie.  
4. Wpisz nick Fortnite i sprawdź dane SOLO/DUO/SQUAD.  

