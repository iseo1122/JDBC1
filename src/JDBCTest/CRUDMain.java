package JDBCTest;

import java.util.Scanner;

public class CRUDMain {
    public static final String black = "\u001B[30m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String white = "\u001B[37m";

    public static final String exit = "\u001B[0m";

    public static void main(String[] args) {



        // CRUD 객체 생성
        CRUD crud = new CRUD();

        // EMP 테이블 조회
        // crud.selectEMP();

        Scanner sc = new Scanner(System.in);
        boolean run = true;
        int menu = 0;

        while(run){
            System.out.println("===========================================");
            System.out.println("[1]connect\t\t[2]insert\t\t[3]select");
            System.out.println("[4]update\t\t[5]delete\t\t[6]close");
            System.out.println("===========================================");
            System.out.print("선택 >> ");

            menu = sc.nextInt();

            switch (menu){
                case 1:
                    crud.connect();
                    break;
                case 2:
                    crud.insert();
                    break;
                case 3:
                    crud.select();
                    break;
                case 4:
                    crud.update();
                    break;
                case 5:
                    crud.delete();
                    break;
                case 6:
                    crud.conClose();
                    run = false;
                    break;
                default:
                    System.out.println("다시 입력하세요.");
                    break;
            }
        }

    }

}
