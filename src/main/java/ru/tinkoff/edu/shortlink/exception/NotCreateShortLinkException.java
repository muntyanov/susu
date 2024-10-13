package ru.tinkoff.edu.shortlink.exception;

public class NotCreateShortLinkException extends Exception{
    public NotCreateShortLinkException(){
       super("Не удалось создать короткую ссылку. Попробуйте еще раз");
    }
}
