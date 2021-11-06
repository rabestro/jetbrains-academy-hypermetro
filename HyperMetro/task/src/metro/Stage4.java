package metro;

import metro.entity.MetroMap;

import java.io.IOException;

public class Stage4 {
    public static void main(String[] args) throws IOException {
        var metro = MetroMap.from(args[0]);

        System.out.println(metro.getLines());
    }
}
