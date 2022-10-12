import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//ATM系統入口類別
public class ATMSystem {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();//存賬戶
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("===================ATM系統===================");
            System.out.println("1.帳戶登陸");
            System.out.println("2.帳戶開戶");

            System.out.println("請選擇操作：");
            int command = sc.nextInt();

            switch (command) {
                case 1:
                    //登陸

                    break;
                case 2:
                    //開戶
                    regisetr(accounts,sc);
                    break;
                default:
                    System.out.println("您輸入的操作不存在OuO");
            }
        }
    }

    /**
     * 開戶方法實作
     * @param accounts 接收帳戶集合
     */
    private static void regisetr(ArrayList<Account> accounts,Scanner sc) {
        System.out.println("=======================系統開戶操作========================");
        Account account = new Account();//創建一個物件用於封裝帳戶訊息

        System.out.println("請輸入您的帳戶名稱：");
        String Userame = sc.next();
        account.setUserName(Userame);
        while (true) {
            System.out.println("請輸入您的帳戶密碼：");
            String Password = sc.next();
            System.out.println("請再次輸入您的帳戶密碼用於確認：");
            String OkPassword = sc.next();
            if (OkPassword.equals(Password)) {
                //密碼認證通過,可以添加到帳戶物件
                account.setPassword(OkPassword);
                break;
            } else {
                System.out.println("很抱歉,您輸入的2次密碼不一致,請重新輸入！");
            }
        }
        System.out.println("請您輸入當次最多可以領取現金額度：");
        double quotaMoney = sc.nextDouble();//限額
        account.setQuotamoney(quotaMoney);

        //隨機生成卡號實作且與其卡號不重複
        String carId = getRandomCardId(accounts);
        account.setCardId(carId);

        //3.把帳戶物件添加到帳戶集合去
        accounts.add(account);
        System.out.println("感謝您," + Userame +" 您的帳戶已經開戶完畢,您的卡號是: "+ carId + ",請您妥善保存此卡號");
    }

    /**
     * 為帳號生出13位與其他用戶卡號不一樣的號碼
     * @return
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random rd = new Random();
        while (true) {
            //1.生成13位數字
            String carId = "";

            for (int i = 0; i < 13; i++) {
                carId += rd.nextInt(10);
            }

            //2.判斷13位卡號與其他帳戶卡號不重複
            //根據這個卡號查詢帳戶物件
            Account acc = getAccountByCardId(carId, accounts);
            if (acc == null) {
                //代表cardId 此時沒有重複 這個卡 號是一個新卡號,可以使用這個卡號作新用戶卡號
                return carId;
            }
        }
    }

    /**
     * 根據卡號查詢出一個帳戶物件出來
     * @param carId 卡號
     * @param accounts 全部帳號集合
     * @return 帳戶物件｜null
     */
    private static Account getAccountByCardId(String carId,ArrayList<Account> accounts){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(carId)){
                return acc;
            }
        }
        return null;//查無此帳戶
    }
}
