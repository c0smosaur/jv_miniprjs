package kr.excel.resume;

import java.util.*;

import java.util.Scanner;

public class ResumeView {
    private Scanner scanner;
    public ResumeView() {
        scanner = new Scanner(System.in);
    }

    // O
    public PersonInfo inputPersonInfo(){
        System.out.println("PERSONAL INFO");
        System.out.println("Photo filename: ");
        String photo = scanner.nextLine();
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Email: ");
        String email=scanner.nextLine();
        System.out.println("Address: ");
        String address = scanner.nextLine();
        System.out.println("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Birthdate(ex: 2000-01-01): ");
        String birthDate = scanner.nextLine();

        return new PersonInfo(photo, name, email,address,phoneNumber,birthDate);
    }

    // X
    public List<Education> inputEducationList(){
        List<Education> educations = new ArrayList<>();

        System.out.println("EDUCATION INFO");
        while (true){
            System.out.println("졸업년도, 학교명, 전공, 졸업여부 (종료는 q)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")){
                break;
            }
            String[] splitInput = input.split(", ");
            if (splitInput.length != 4){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
                continue;
            }

            Education education = new Education(splitInput[0],splitInput[1],splitInput[2],splitInput[3]);
            educations.add(education);
        }
        return educations;
    }

    // X
    public List<Career> inputCareerList(){
        List<Career> careers = new ArrayList<>();

        System.out.println("CAREERS INFO");
        while (true){
            System.out.println("근무기간, 근무처, 담당업무, 근속연수 (종료는 q)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")){
                break;
            }
            String[] splitInput = input.split(", ");
            if (splitInput.length!=4){
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                continue;
            }
            Career career = new Career(splitInput[0],splitInput[1],splitInput[2],splitInput[3]);
            careers.add(career);
        }
        return careers;
    }

    // X
    public String inputSelfIntroduction(){
        // 자기소개서 저장
        System.out.println("SELF-INTRODUCTION");
        System.out.println("입력을 마치려면 빈 줄을 입력하세요.");
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = scanner.nextLine()).trim().length() > 0){
            sb.append(line).append("\n");
        }
        return sb.toString().trim();
    }




}
