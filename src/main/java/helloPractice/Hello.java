package helloPractice;

public class Hello {
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

/*
 * Java Bean 규약에 따라 private 멤버 변수 선언 후 이를 getter, setter 메서드를 통해 접근함
 * 프로퍼티 접근 방식이라고도 함
 * 
 * 
 * Java Bean 규약
 * 
 * 1. Java Bean 클래스는 기본 패키지 이외의 특정 패키지에 속해야한다
 * 2. 기본 생성자가 존재해야한다. -> 오버라이딩 되지 않은 생성자가 존재해야한다
 * 3. 멤버 변수의 접근 제어자는 private 로 선언되어야 한다
 * 4. 멤버 변수에 접근 가능한 getter, etter 메서드가 존재해야한다
 * 5. getter, setter는 접근자 public으로 선언되어야 한다
 * 5-1. 배열의 경우 배열의 일부 값을 설정하거나 가져올 수 있는 getter, setter를 만들 수 있다
 * 6. 객체를 입출력에 사용할 수 있도록 바이트 형태로 변환시키는 객체 직렬화 관련 인터페이스
 * java.io.Serializable 인터페이스를 상속하여 직렬화 한다(선택사항)
 * 6-1. 객체를 바이트 스트림으로 변환하는 것을 마샬링, 반대는 언마샬링이라고 함
 * 
 * Java Bean 규약을 통해 Java Bean 규약을 사용하는 기술을 사용할 수 있다.
 * 
 * 
 */