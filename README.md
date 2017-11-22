# StockData
Android Application

This application intends to call the QUANDL API to fetch historical stock data for COF, GOOGL and MSFT and perform analysis on their respective datasets. 

To install the codebase :
1) Install Android Studio/IntelliJ IDEA 
2) Clone the repository
3) Open the project through IDE.
4) Create an emulator using Android AVD or connect an device to machine. 
5) Hit run on the task bar and start the application on either emulator or physical device. 

In the application, following features are implemented.
1) Tables indicating monthly average open and average close for each of the securities. 

2) Max daily profit for each of the security and the day in the given time range, when it reached the max. 

3) The number of days for each security when the trading volume exceeded its average by 10%. 

4) The number of days for each security when the closing price was less than opening price. 

5) Unit test has been added to validate the calculations. 
