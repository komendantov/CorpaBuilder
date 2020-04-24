package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.komendantov.corpabuilder.models.Text;
import ru.komendantov.corpabuilder.models.Word;
import ru.komendantov.corpabuilder.repositories.TextRepository;
import ru.komendantov.corpabuilder.services.MystemService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class HeyController {
    
    @Autowired
    private TextRepository textRepository;

    @Autowired
    private MystemService mystemService;
//    public HeyController(MystemService mystemService){
//
//    }

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @PostMapping("/hey")
    public String getHey(@RequestParam(name = "title") String title, @RequestParam(name = "text") String text, Model model) throws IOException, InterruptedException {
        //   model.addAttribute("text",text);
        ObjectMapper objectMapper = new ObjectMapper();
        String c = mystemService.analyseText(text);

        List<Word> w = objectMapper.readValue(c, new TypeReference<List<Word>>() {
        });
        Text textt = new Text();
        textt.setTitle(title);
        textt.setWords(w);
        textRepository.save(textt);
        model.addAttribute("list", w);

        List<Text> getedFromBd = textRepository.getAllByTitle(title);
        //wordRepository.saveAll();

        List<Text> ggg = textRepository.getAllByWordsAnalysisLex("проверка");
        //  wordRepository.findByText(text);
        return "hey2";
    }

    @PostMapping("/rest")
    public ResponseEntity<String> rest() {
        return null;
    }

}