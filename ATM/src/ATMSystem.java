import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
        System.out.println("=====================ATM======================");
        System.out.println("1.登陸");
        System.out.println("2.開戶");

        System.out.println("請選擇操作");
        int command = sc.nextInt();
            switch (command) {
                case 1://登陸
                    login(accounts,sc);
                    break;
                case 2://開戶
                    register(accounts,sc);
                    break;
                default:
                    System.out.println("輸入錯誤操作請重新選擇");
            }
        }
    }

    /**
     *  開戶功能實作
     * @param accounts 接收帳戶集合
     */
    public static void register(ArrayList<Account> accounts,Scanner sc){
        System.out.println("=================開戶操作=================");
        //1.創建一個帳戶物件，用於封裝帳戶訊息
        Account account = new Account();

        //2.輸入帳戶訊息,丟到account物件去
        System.out.println("請妳輸入帳戶名稱：");
        String username = sc.next();
        account.setUserName(username);
       while (true) {
           System.out.println("請妳輸入帳戶密碼：");
           String password = sc.next();
           System.out.println("再次驗證密碼(用於驗證)：");
           String checkpassword = sc.next();
           if (checkpassword.equals(password)) {
               //密碼驗證正確將password丟進去
               account.setPassWord(checkpassword);
               break;
           } else {
               System.out.println("驗證密碼錯誤請重新輸入");
           }
       }

        System.out.println("請輸入帳戶單次領取現金限額：");
        double quotamoney = sc.nextDouble();
        while (true) {
            if (quotamoney > 0) {
                account.setQuotamoney(quotamoney);
                break;
            } else {
                System.out.println("輸入錯誤請重新輸入!");
            }
        }

        //隨機生成13位與其他帳戶不同的卡號(獨立功能,獨立方法)
        String cardId = getRandomCardId(accounts);
        account.setCardId(cardId);

        //3.把帳戶物件添加到帳戶集合去
        accounts.add(account);
        System.out.println("恭喜您，"+username+"您開戶成功，您的卡號為："+cardId+"請妥善保管此卡號");
    }

    /**
     * 位帳戶生成13位與其他帳戶不同卡號
     * @return
     */
    public static String  getRandomCardId(ArrayList<Account> accounts){
        Random rd = new Random();
        while (true) {
            //1.生成13位數字
            String cardId = "";

            for (int i = 0; i < 13; i++) {
                cardId += rd.nextInt(10);
            }

            //2.判斷13位卡號是否跟別的帳戶卡號重複
            //根據這個卡號查詢帳戶物件
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc == null) {
                //代表cardId 沒有重複可以用於創建新的帳戶
                return cardId;
            }
        }
    }

    /**
     * 根據卡號查詢一個帳戶出來
     * @param cardIs
     * @param accounts
     * @return 帳戶物件｜null
     */
    private static Account getAccountByCardId(String cardIs,ArrayList<Account>accounts){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(cardIs)){
                return acc;
            }
        }
        return null;//查無帳號
    }


    /**
     * 登陸方法實作
     * @param accounts  接受帳戶集合
     * @param sc 掃瞄器
     */
    private static void login(ArrayList<Account>accounts,Scanner sc){
        System.out.println("====================登陸介面====================");
        //1.判斷帳戶中是否存在帳戶
        if (accounts.size() == 0){
            System.out.println("當前系統中無任何帳戶，請先建立帳戶");
            return;
        }
        while (true) {
            //2.登陸操作
            System.out.println("請輸入您的卡號：");
            String cardId = sc.next();
            //3.判斷卡號是否存在，去帳戶集合中查詢帳戶物件
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc != null) {
                while (true) {
                    //卡號存在
                    //4.輸入密碼，驗證密碼
                    System.out.println("請輸入登陸密碼：");
                    String password = sc.next();
                    //判斷密碼是否正確
                    if (acc.getPassWord().equals(password)) {
                        //登陸成功
                        System.out.println("恭喜您，"+ acc.getUserName()+" 登陸成功 您的卡號是："+acc.getCardId());

                    } else {
                        System.out.println("密碼輸入錯誤請重新輸入");
                    }
                }
            } else {
                System.out.println("系統中不存在該帳戶卡號！");
            }
        }
    }
}
