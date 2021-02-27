package UUID;

import java.util.UUID;

/**
 * @ClassName: UuIdTesst
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/26
 * @Since version-1.0
 **/
public class UuIdTesst {
    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        System.out.println (uuid);
    }
}
