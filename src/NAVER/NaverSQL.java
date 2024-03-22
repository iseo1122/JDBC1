package NAVER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NaverSQL {

    // DB연동 3객체
    Connection con;             // 접속
    PreparedStatement pstmt;    // SQL문
    ResultSet rs;               // 결과

    // DB접속 메소드
    public void connect() {
        con = DBC.DBConnect();
    }

    // DB해제 메소드
    public void conClose(){
        try {
            con.close();    // 접속한 DB를 닫다(close)
            // 발생할 있는 예외 => 접속을 하지 않은 상태에서 해제를 할 경우
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 회원가입 메소드 : NaverMain에서 입력 받은 정보(NaverMember member)를 매개변수로 사용
    public void memberJoin(NaverMember member) {

        try {
            // (1) sql문 작성 : 입력할 데이터 대신 '?' 작성
            String sql = "INSERT INTO NAVER VALUES(?, ? ,? ,? ,? ,? ,?)";

            // (2) db 준비
            pstmt = con.prepareStatement(sql);

            // (3) sql문에서 '?' 데이터 처리

            // sql문의 1(첫번째) '?'에 member객체의 nId(getnId()로 불러온)값을 입력
            pstmt.setString(1, member.getnId());
            pstmt.setString(2, member.getnPw());
            pstmt.setString(3, member.getnEmail());
            pstmt.setString(4, member.getnName());
            pstmt.setString(5, member.getnBirth());
            pstmt.setString(6, member.getnGender());
            pstmt.setString(7, member.getnPhone());
            // 물음표의 번호, getter로 받아오는 데이터(이름 꼭 확인)

            // (4) 실행 : insert, update, delete(int result), select(ResultSet rs)
            int result = pstmt.executeUpdate();

            // (5) 결과처리
            if(result > 0){
                System.out.println("가입 성공!");
            } else {
                System.out.println("가입 실패!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void memberList() {

        // 한사람의 회원정보 member객체
        NaverMember member = new NaverMember();

        // 회원목록을 만들수 있는 memberList
        ArrayList<NaverMember> memberList = new ArrayList<>();


        /*
            Array(배열), List(목록) => 데이터를 저장하는데 사용되는 구조

            - Array : 크기가 고정되어 있다.
                    : 기본타입(정수형, 실수형, 문자형, 논리형)

            - List : 크기가 변한다.
                   : 참조형(객체)

            - List, ArrayList 비교 : List는 인터페이스, ArrayList는 List의 구현클래스
        */



        try {
            // (1) sql문 작성
            String sql = "SELECT NID, NPW, NEMAIL, NNAME, TO_CHAR(NBIRTH, 'YYYY-MM-DD'), NGENDER, NPHONE FROM NAVER";

            // (2) db 준비
            pstmt = con.prepareStatement(sql);

            // (3) 데이터 입력 (?가 없을 경우 생략)

            // (4) 실행 : select -> rs
            rs = pstmt.executeQuery();

            // (5) 결과
            while(rs.next()){
//                System.out.print("| 아이디 : " + rs.getString(1));
//                System.out.print("\t| 이름 : " + rs.getString(4));
//                System.out.println("\t| 연락처 : " + rs.getString(7) + "\t|");


                member.setnId(rs.getString(1));
                // member객체(한사람의 정보를 입력할 수 있는 종이)에
                // setnId()메소드를 사용해서 아이디 정보를 입력한다.
                // rs.getString(1) : db에서 조회한 내용중에 첫번째 컬럼에 있는 데이터를

                member.setnPw(rs.getString(2));
                member.setnEmail(rs.getString(3));
                member.setnName(rs.getString(4));
                member.setnBirth(rs.getString(5));
                member.setnGender(rs.getString(6));
                member.setnPhone(rs.getString(7));

                memberList.add(member);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(int i=0; i<memberList.size(); i++){
            System.out.println(memberList.get(i));
        }

//        for(NaverMember list : memberList){
//            System.out.println(list);
//        }



    }

    public boolean memberLogin(String nId, String nPw) {
        boolean result = false;


        try {
            // (1) sql문 작성
            String sql = "SELECT * FROM NAVER WHERE NID=? AND NPW=?";

            // (2) db 준비
            pstmt = con.prepareStatement(sql);

            // (3) 데이터 입력
            pstmt.setString(1, nId);
            pstmt.setString(2, nPw);

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void myInfo(String nId) {



        try {
            // (1) sql문 작성
            // String sql = "SELECT * FROM NAVER WHERE NID=?";
            String sql = "SELECT NID, NPW, NEMAIL, NNAME, TO_CHAR(NBIRTH, 'YYYY-MM-DD'), NGENDER, NPHONE FROM NAVER WHERE NID=?";

            // (2) db 준비
            pstmt = con.prepareStatement(sql);

            // (3) 데이터 입력
            pstmt.setString(1, nId);

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            if(rs.next()) {
                System.out.println("아이디\t:\t" + rs.getString(1));
                System.out.println("비밀번호\t:\t" + rs.getString(2));
                System.out.println("이메일\t:\t" + rs.getString(3));
                System.out.println("이  름\t:\t" + rs.getString(4));
                System.out.println("생년월일\t:\t" + rs.getString(5));
                System.out.println("성  별\t:\t" + rs.getString(6));
                System.out.println("연락처\t:\t" + rs.getString(7));
                System.out.println();
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }





    }

    // 회원 수정 메소드(가입 메소드와 매우 비슷하다!)
    public void memberModify(NaverMember member) {
        try {
            // (1) sql문 작성 : 입력할 데이터 대신 '?' 작성
            String sql = "UPDATE NAVER SET NPW=?, NEMAIL=?, NNAME=?, NBIRTH=?, NGENDER=?, NPHONE=? WHERE NID=?";

            // (2) db 준비
            pstmt = con.prepareStatement(sql);

            // (3) sql문에서 '?' 데이터 처리

            // 수정할 컬럼들
            pstmt.setString(1, member.getnPw());
            pstmt.setString(2, member.getnEmail());
            pstmt.setString(3, member.getnName());
            pstmt.setString(4, member.getnBirth());
            pstmt.setString(5, member.getnGender());
            pstmt.setString(6, member.getnPhone());

            // 기준이 되는 컬럼 NID가 7번째 '?'
            pstmt.setString(7, member.getnId());

            // (4) 실행 : insert, update, delete(int result), select(ResultSet rs)
            int result = pstmt.executeUpdate();

            // (5) 결과처리
            if(result > 0){
                System.out.println("수정 성공!");
            } else {
                System.out.println("수정 실패!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void memberDelete(String nId) {

        try {
            // (1)
            String sql = "DELETE FROM NAVER WHERE NID=?";

            // (2)
            pstmt = con.prepareStatement(sql);

            // (3)
            pstmt.setString(1, nId);

            // (4)
            int result = pstmt.executeUpdate();

            // (5)
            if(result>0){
                System.out.println("삭제 성공!");
            } else {
                System.out.println("삭제 실패!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
