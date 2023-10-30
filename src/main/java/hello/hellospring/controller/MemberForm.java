package hello.hellospring.controller;

public class MemberForm {
    private String name; // input 태그의 name 어트리뷰트랑 이름이 같아야함

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
