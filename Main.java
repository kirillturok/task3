package com.company;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import java.security.SecureRandom;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        /*Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String[] arg = str.split("\\s+");*/
        checkEnter(args);
        SecureRandom random = new SecureRandom();
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        int half = (args.length-1)/2;
        while(true){
            byte key[] = new byte[16];
            random.nextBytes(key);
            System.out.println("HMAC:\n"+ Hex.toHexString(digestSHA3.digest(key)));
            System.out.println("Available moves:");
            for(int i=0;i<args.length;i++) System.out.println(i+1+" - "+args[i]);
            System.out.println("0 - exit");
            int move=getMove(args);
            System.out.println("Your move: "+args[move]);
            int comp = Math.abs(key[15]%args.length);
            System.out.println("Computer move: "+args[comp]);
            whoWins(move, comp, half);
            System.out.println( Hex.toHexString(key));
            System.out.println("-----");
        }
    }

    static void checkEnter(String[] arg){
        if(arg.length<3||arg.length%2==0){
            System.out.println("Количество слов должно быть нечетным и <=3.");
            System.exit(0);
        }
        for(int i=0;i<arg.length-1;i++)
            for(int j=i+1;j<arg.length;j++)
                if(arg[i].equals(arg[j])){
                    System.out.println("Не должно быть повторяющихся слов.");
                    System.exit(0);
                }
    }

    static int getMove(String[] arg){
        Scanner scan = new Scanner(System.in);
        int move;
        while(true) {
            System.out.print("Enter your move: ");
            while (!scan.hasNextInt()) {
                System.out.println("Необходимо ввести целое число.");
                System.out.print("Enter your move: ");
                scan.next();
            }
            move = scan.nextInt();
            if (move < 0 || move > arg.length) {
                System.out.println("Необходимо ввести число из меню.");
                continue;
            }
            if (move == 0) System.exit(0);
            break;
        }
        move--;
        return move;
    }

    static void whoWins(int move, int comp, int half){
        if(move==comp) {System.out.println("Ничья.");return;}
        if(move>=half){
            if(comp<move&&comp>=(move-half)) {
                System.out.println("Ваша победа!");
                return;
            }else{
                System.out.println("Вы проиграли.");
                return;
            }
        }else{
            if(comp>move&&comp<=(move+half)){
                System.out.println("Вы проиграли.");
                return;
            }else{
                System.out.println("Ваша победа!");
                return;
            }
        }
    }
}
