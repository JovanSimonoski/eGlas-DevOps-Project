package mk.ukim.finki.eglas.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UtilService {
    Map<Character, String> cyrillicToLatinMap;

    public UtilService() {
        cyrillicToLatinMap = new HashMap<>();
        cyrillicToLatinMap.put('а', "a");
        cyrillicToLatinMap.put('б', "b");
        cyrillicToLatinMap.put('в', "v");
        cyrillicToLatinMap.put('г', "g");
        cyrillicToLatinMap.put('д', "d");
        cyrillicToLatinMap.put('ѓ', "gj");
        cyrillicToLatinMap.put('е', "e");
        cyrillicToLatinMap.put('ж', "zh");
        cyrillicToLatinMap.put('з', "z");
        cyrillicToLatinMap.put('ѕ', "dz");
        cyrillicToLatinMap.put('и', "i");
        cyrillicToLatinMap.put('ј', "j");
        cyrillicToLatinMap.put('к', "k");
        cyrillicToLatinMap.put('л', "l");
        cyrillicToLatinMap.put('љ', "lj");
        cyrillicToLatinMap.put('м', "m");
        cyrillicToLatinMap.put('н', "n");
        cyrillicToLatinMap.put('њ', "nj");
        cyrillicToLatinMap.put('о', "o");
        cyrillicToLatinMap.put('п', "p");
        cyrillicToLatinMap.put('р', "r");
        cyrillicToLatinMap.put('с', "s");
        cyrillicToLatinMap.put('т', "t");
        cyrillicToLatinMap.put('ќ', "kj");
        cyrillicToLatinMap.put('у', "u");
        cyrillicToLatinMap.put('ф', "f");
        cyrillicToLatinMap.put('х', "h");
        cyrillicToLatinMap.put('ц', "c");
        cyrillicToLatinMap.put('ч', "ch");
        cyrillicToLatinMap.put('џ', "dz");
        cyrillicToLatinMap.put('ш', "sh");
    }

    public String cyrillicToLatinTransliteration(String cyrillic) {
        StringBuilder sb = new StringBuilder();
        return cyrillic.chars().mapToObj(x -> {
            String transl = cyrillicToLatinMap.get(Character.toLowerCase((char) x));
            if(transl == null) {
                return String.valueOf((char) x);
            }
            return transl;
        }).collect(Collectors.joining());
    }
}
