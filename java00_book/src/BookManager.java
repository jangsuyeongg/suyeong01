
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import BookInfor.BookDataSet;
import BookInfor.BookVO;
import BookInfor.JoinDataSet;
import BookInfor.JoinVO;


public class BookManager {
	Scanner scan = new Scanner(System.in);
	
	public BookManager() {	
		
	}
	
	public void bookStart() {//처음시작!!
		do {
			try {
				JoinDataSet.basicJoinSet();
					Scanner scan = new Scanner(System.in);
					System.out.println("==============================[메뉴를 선택하세요.]=================================");
					System.out.println("[1]회원가입  [2]사용자로그인  [3]관리자로그인  [4]로그아웃  =>>");
					int a = scan.nextInt();
					System.out.println("==================================================================================");
				
					if(a==1) {		
						allJoin();//로그인
						login();
						JoinDataSet.basicJoinSet();
						userStart();
					}else if(a==2) {
						login();
						JoinDataSet.basicJoinSet();
						userStart();//사용자모드 시작
					}else if(a==3) {
						managerStart();//관리자모드 시작
					}else if(a==4){
						logout();//로그아웃
						
				}
			}catch(Exception e) {
				System.out.println("잘못누르셨습니다.");
			}
		}while(true);
			
		}
	
	public void managerStart() {
		boolean logResult=false;
		do {
			//관리자 로그인!! (id,pw 고정) - admin / 1234 
			String userid = inData("아이디");
			String userpwd = inData("비밀번호");
			Login log = new Login();
			logResult = log.loginCheck(userid,userpwd);
		}while(!logResult);

	//	BookDataSet.basicBookSet();//책목록
	//	allBookList();//책목록 출력하기
		
		System.out.println("==================================================================================");
		System.out.println("관리자모드로 로그인 되었습니다.");
		System.out.println("==================================================================================");
		String menuTitle = "메뉴[1.책목록 2.책등록 3.책수정 4.삭제 5.회원목록 6.종료 7.로그아웃]";
		do {
			String menu = inData(menuTitle);
			if(menu.equals("1")) {// 책목록
				allBookList();
			}else if(menu.equals("2")) {//책등록
				bookInsert();
				allBookList();
			}else if(menu.equals("3")) { //책수정
				bookUpdate();
				allBookList();
			}else if(menu.equals("4")) { //삭제
				bookDelete();
				allBookList();	
			}else if(menu.equals("5")){ //회원등록
				JoinDataSet.basicJoinSet();
				allJoinList();
			}else if(menu.equals("6")) { //종료
				System.out.println("프로그램이 종료되었습니다.");
				System.exit(0);	
			}else if(menu.equals("7")){
				logout();
			}else {
				System.out.println("메뉴를 잘못선택하였습니다. 다시 입력하세용용");
			}
			
		}while(true);
	}
	// 도서추가
	public void bookInsert() { 
		BookVO vo = new BookVO();
		
		vo.setCode(inData("도서코드"));
		vo.setBookName(inData("책이름"));
		vo.setAuthor(inData("저자"));
		vo.setCompany(inData("출판사"));
		vo.setRental(inData("구입상태"));
		vo.setRentaldate(inData("가격"));
		
		BookDataSet.bookList.put(vo.getBookName(), vo);
	}
	
	//도서수정
	public void bookUpdate() { 
		String editName = inData("수정할 책이름입력");
		
		String editMenu= inData("수정할 필드선택[1.구입상태, 2.가격]");
		if(editMenu.equals("1")) {
			rentalUpdate(editName);
		}else if(editMenu.equals("2")){
			rentaldateUpdate(editName);
		}else {
			System.out.println("메뉴를 잘못입력하셨습니다.");
		}
	}
	
	public void rentalUpdate(String username) {  //구입상태
		String rental = inData("수정할 구입상태 입력(구입가능, 구입완료)");
		BookVO vo = BookDataSet.bookList.get(username);
		vo.setRental(rental);
	}
	public void rentaldateUpdate(String username) { //금액
		String rentaldate = inData("수정할 금액 입력");
		BookVO vo = BookDataSet.bookList.get(username);
		vo.setRentaldate(rentaldate);
	}
	
	//도서삭제
	public void bookDelete() {
		String delName = inData("삭제할 책이름");
		BookDataSet.bookList.remove(delName);
	}
	
	

	public void bookSearch() {//책검색
		
			try {
				String title = inData("책이름");
				Set<String> keys = BookDataSet.bookList.keySet();
				Iterator<String> ii = keys.iterator();
				while(ii.hasNext()) {
					String key = ii.next();
		
					if(title.equals(key)) {
						BookVO value = BookDataSet.bookList.get(key);
						
						System.out.printf("%s %10s %10s %10s %10s %12s\n","도서코드","책이름","저자","출판사","구입상태","가격");
						System.out.println("==================================================================================");
						System.out.print(value.getCode());
						System.out.print("\t    "+value.getBookName());
						System.out.print("\t "+value.getAuthor());
						System.out.print("\t    "+value.getCompany());
						System.out.print("\t  "+value.getRental());
						System.out.print("\t      "+value.getRentaldate()+"\n");        
					}
				}
			}catch(Exception e) {
				System.out.println("에러입니다용. 다시입력하세요.");
			}
	 }
	
	public void bookSale() {//책구입
			try {
			String saleName = inData("구입할 책이름=");
				stateUpdate(saleName);
			}catch(Exception e) {
				System.out.println("원하시는 책이 없습니다. 다시입력하세요.");
			}
		}
	
	public void stateUpdate(String bookname) {
		String sale = ("구입완료");
		BookVO vo = BookDataSet.bookList.get(bookname);
		vo.setRental(sale);
		
	}
	public void logout() {
		System.out.println("로그아웃합니다.");
		bookStart();
		
	}
	//회원가입
	public void allJoin() {
		JoinVO vo = new JoinVO();
		vo.setId(inData("아이디"));
		vo.setPassword(inData("비밀번호"));
		vo.setName(inData("이름"));
		vo.setTel(inData("전화번호"));
		JoinDataSet.joinList.put(vo.getId(), vo);
		System.out.println("회원가입이 완료되었습니다. 로그인하세요.");
		System.out.println("==================================================================================");
	}
	//로그인 
	public void login() {
		boolean canEscape = false;
		do {
			try {
				System.out.println("아이디와 비밀번호를 입력하세요.");
				System.out.print("아이디=");
				String id = scan.nextLine(); // 받아온 아이디
				String realId = ""; // 실제 아이디
				
				Collection<JoinVO> joinCollection = JoinDataSet.joinList.values();
				Iterator<JoinVO> joinIterator = joinCollection.iterator();
				while(joinIterator.hasNext()) {
					JoinVO oneMember = joinIterator.next();
					// ID 체크
					if(oneMember.getId().equals(id)) {
						realId = oneMember.getId();
					}
				}
				JoinVO wanted = JoinDataSet.joinList.get(realId); // ID가 일치하는 VO의 정보
		
				// ID가 같은 VO의 비번 받아오기
				System.out.print("비밀번호=");
				String password = scan.nextLine(); // 받아온 비번
				String realPass = wanted.getPassword(); // 실제 비번
				
				
				if(id.equals(realId)) {
					if(password.equals(realPass)) {
						System.out.println("==================================================================================");
						System.out.println("사용자모드로 로그인 되었습니다.");
						canEscape = true;
					}else {
						System.out.println("정보가 일치하지 않습니다.");
						//login();
					}
				}else{
					System.out.println("ID가 존재하지 않습니다.");
				}
				if(canEscape) break;
			}catch(Exception e) {
				System.out.println("다시 입력하세요.");
			}
		} while(true);
	}
			
		
	//사용자모드 
	public void userStart() {
		System.out.println("==================================================================================");
		String menuTitle = "메뉴[1.책목록 2.책검색 3.책구입 4.종료 5.로그아웃]";
		
	//	BookDataSet.basicBookSet();//책목록
	//	allBookList();//책목록 출력하기
		
		do {
			String menu = inData(menuTitle);
			if(menu.equals("1")) {
				
				BookDataSet.basicBookSet();
				allBookList();
			}else if(menu.equals("2")) { 
				 bookSearch();
			}else if(menu.equals("3")) {
				
				bookSale();
				allBookList();//책목록 출력하기
				
			
			}else if(menu.equals("4")) {
				System.out.println("프로그램이 종료되었습니다.");
				System.exit(0);
			}else if(menu.equals("5")) {
				logout();
					
			}else {
				System.out.println("메뉴를 잘못선택하였습니다. 다시 입력하세용용");
			}
			
		}while(true);
	}
	
		//책목록 출력하기
	public void allBookList() {
		BookVO.titlePrint();
		Collection<BookVO> list = BookDataSet.bookList.values();
		Iterator<BookVO> ii = list.iterator();
		while(ii.hasNext()) {
			BookVO vo = ii. next();
			vo.bookPrint(vo);
			
		}
	}
	
		//회원정보 출력하기
	public void allJoinList() {
		JoinVO.jointitle();
		Collection<JoinVO> list = JoinDataSet.joinList.values();
		Iterator<JoinVO> iii = list.iterator();
		while(iii.hasNext()) {
			JoinVO vo = iii. next();
			vo.joinprint();
		}
	}
	public String inData(String msg) {
		System.out.print(msg+"->");
		return scan.nextLine();
	}
}




