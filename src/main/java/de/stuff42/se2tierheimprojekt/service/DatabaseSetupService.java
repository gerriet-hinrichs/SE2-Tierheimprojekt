/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.stuff42.se2tierheimprojekt.service;

import java.util.*;

import de.stuff42.se2tierheimprojekt.data.*;
import de.stuff42.se2tierheimprojekt.entity.*;

import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that fills the database with data.
 */
@Service
public class DatabaseSetupService extends BaseService {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private AnimalDAO animalDAO;

    private int questionSortOrder;

    public DatabaseSetupService() {
        this.questionSortOrder = 1;
    }

    /**
     * Clean database.
     */
    public void clean() {
        answerDAO.deleteAll();
        questionDAO.deleteAll();
        animalDAO.deleteAll();
    }

    /**
     * Fills database with static data.
     */
    public void setup() {

    	final String mainpath = "/static-files/images/shelter_animals/";
    	final String AE = "\u00c4", ae = "\u00e4", OE = "\u00d6", oe = "\u00f6", UE = "\u00dc", ue = "\u00fc", ss = "\u00df";
    	
        // Questions & Answers
        addQuestionWithAnswers("Wieviele Quadratmeter stehen ungef" +ae+ "hr f" +ue+ "r die Tierhaltung zur Verf" +ue+ "gung?", AnswerType.RADIOBUTTON,
                new AnswerContent(" Weniger als 35",
                        new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT)),
                        new HashSet<>(Arrays.asList(AnimalSize.MEDIUM, AnimalSize.HUGE)),
                        null,
                        null,
                        null),
                new AnswerContent("35-55",
                        null,
                        new HashSet<>(Arrays.asList(AnimalSize.MEDIUM, AnimalSize.HUGE)),
                        null,
                        null,
                        null),
                new AnswerContent("Mehr als 55",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Kann das Tier auch im Garten gehalten werden?", AnswerType.RADIOBUTTON,
                new AnswerContent("Ja",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Nein",
                        null,
                        null,
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.SMALL, AnimalGardenSpace.MEDIUM, AnimalGardenSpace.HUGE)))
        );
        addQuestionWithAnswers("In welcher Umgebung wird das Tier gehalten?", AnswerType.RADIOBUTTON,
                new AnswerContent("Land / Dorf",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Wohngebiet ohne Hauptstra" +ss+ "e in direkter Umgebung",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("An einer Hauptstra" +ss+ "e oder im Stadtkern",
                        new HashSet<>(Collections.singletonList(AnimalType.DOG)),
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Wieviele Stunden hast du durchschnittlich t" +ae+ "glich Zeit f" +ue+ "r das Tier?", AnswerType.RADIOBUTTON,
                new AnswerContent("Bis zu 1",
                        new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT, AnimalType.BIRD)),
                        new HashSet<>(Arrays.asList(AnimalSize.MEDIUM, AnimalSize.HUGE)),
                        null,
                        new HashSet<>(Arrays.asList(AnimalCareTyp.SOME, AnimalCareTyp.MUCH)),
                        null),
                new AnswerContent("1-4",
                        new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.BIRD)),
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalCareTyp.SOME, AnimalCareTyp.MUCH)),
                        null),
                new AnswerContent("5-8",
                        null,
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalCareTyp.SOME, AnimalCareTyp.MUCH)),
                        null),
                new AnswerContent("Den ganzen Tag",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Soll das Tier auch von Kindern geplegt werden?", AnswerType.RADIOBUTTON,
                new AnswerContent("Ja",
                        new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.BIRD, AnimalType.BUNNY)),
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalCareTyp.SOME, AnimalCareTyp.MUCH)),
                        null),
                new AnswerContent("Nein",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Wie hoch sollten ungef" +ae+ "hr die monatlichen Kosten sein? (Ohne Grundausstattung)", AnswerType.RADIOBUTTON,
                new AnswerContent("20-30",
                        new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT)),
                        new HashSet<>(Arrays.asList(AnimalSize.MEDIUM, AnimalSize.HUGE)),
                        null,
                        null,
                        null),
                new AnswerContent("30-60",
                        new HashSet<>(Collections.singletonList(AnimalType.DOG)),
                        new HashSet<>(Collections.singletonList(AnimalSize.HUGE)),
                        null,
                        null,
                        null),
                new AnswerContent("60-80",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Interessierst du dich f" +ue+ "r eine bestimmte Tierart?", AnswerType.CHECKBOX,
        		new AnswerContent("Keine bestimmte",
        				null,
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Hund",
        				new HashSet<>(Arrays.asList(AnimalType.CAT, AnimalType.BIRD, AnimalType.FISH, AnimalType.REPTILE, AnimalType.HAMSTER, AnimalType.BUNNY, AnimalType.GUINEA_PIG, AnimalType.MOUSE)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Katze",
        				new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.BIRD, AnimalType.FISH, AnimalType.REPTILE, AnimalType.HAMSTER, AnimalType.BUNNY, AnimalType.GUINEA_PIG, AnimalType.MOUSE)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Vogel",
        				new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT, AnimalType.FISH, AnimalType.REPTILE, AnimalType.HAMSTER, AnimalType.BUNNY, AnimalType.GUINEA_PIG, AnimalType.MOUSE)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Fisch",
        				new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT, AnimalType.BIRD, AnimalType.REPTILE, AnimalType.HAMSTER, AnimalType.BUNNY, AnimalType.GUINEA_PIG, AnimalType.MOUSE)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Reptil",
        				new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT, AnimalType.BIRD, AnimalType.FISH, AnimalType.HAMSTER, AnimalType.BUNNY, AnimalType.GUINEA_PIG, AnimalType.MOUSE)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Kleintiere (Hamster, Kaninchen, Meerschweinchen, M" +ae+ "use, u." +ae+ ".)",
        				new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT, AnimalType.BIRD, AnimalType.FISH, AnimalType.REPTILE)),
        				null,
        				null,
        				null,
        				null)
        );

        // Dogs
        addAnimal("Didi", "Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.DOG, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "didi.jpg");
        addAnimal("Slushi", "Jack-Russell-Terrier-Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.DOG, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, "");
        addAnimal("Sandor", "Malinois", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.HUGE,
                AnimalType.DOG, AnimalSize.HUGE, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "sandor.jpg");
        addAnimal("Thelma", "Mischling", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.DOG, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.HUGE, "");
        addAnimal("Anuk", "Thai Ridgeback", AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
                AnimalType.DOG, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.NONE, mainpath + "anuk.jpg");
        addAnimal("Jimbo", "Pinscher-Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
        		AnimalType.DOG, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "jimbo.jpg");
        addAnimal("Bowee", "Mischling", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.MEDIUM,
        		AnimalCost.EXPENSIVE, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "bowee.jpg");
        addAnimal("Joschi", "Jagdterrier-Mischling", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL, AnimalType.DOG, AnimalSize.SMALL,
        		AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "joschi.jpg");
        addAnimal("Annelie", "Mischling", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL, AnimalType.DOG, AnimalSize.SMALL,
        		AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "annelie.jpg");
        addAnimal("Willie", "Mischling", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM, AnimalType.DOG, AnimalSize.MEDIUM,
        		AnimalCost.MEDIUM, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "willie.jpg");
        addAnimal("Ozzy", "Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.NONE, AnimalType.DOG, AnimalSize.SMALL,
        		AnimalCost.CHEAP, AnimalCareTyp.MUCH, AnimalGardenSpace.NONE, mainpath + "ozzy.jpg");
        addAnimal("Pia", "Mischling", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM, AnimalType.DOG, AnimalSize.MEDIUM,
        		AnimalCost.MEDIUM, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "pia.jpg");
        addAnimal("Belinda", "Mischling", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM, AnimalType.DOG, AnimalSize.MEDIUM,
        		AnimalCost.MEDIUM, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "belinda.jpg");
        addAnimal("Jazz", "Mischling", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.HUGE,
        		AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.HUGE, mainpath + "jazz.jpg");
        addAnimal("Gwenny", "Mischling", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.HUGE,
        		AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "gwenny.jpg");
        addAnimal("Sheila", "American-Staffordshire-Terrier-Mischling", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.HUGE,
        		AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "sheila.jpg");
        addAnimal("Pancho", "Sch�ferhund-Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.HUGE,
        		AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "pancho.jpg");
        addAnimal("Schnipsel", "Staffordshire-Bullterrier-Mischling", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.MEDIUM,
        		AnimalCost.MEDIUM, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "schnipsel.jpg");
        

        // Cats
        final String euroKurz = "Europ" +ae+ "isch Kurzhaar";
        addAnimal("Tobi", "Europ" +ae+ "isch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "tobi.jpg");
        addAnimal("Mia", "Siam", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "mia.jpg");
        addAnimal("Lisa", "Karth" +ae+ "user-Mischling", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "lisa.jpg");
        addAnimal("Jerry", "Europ" +ae+ "isch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "jerry.jpg");
        addAnimal("Paul", "Europ" +ae+ "isch Kurzhaar", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "paul.jpg");
        addAnimal("Sir Simon", "Europ" +ae+ "isch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.HUGE, mainpath + "sir_simon.jpg");
        addAnimal("P" +ue+ "nktchen", "Europ" +ae+ "isch Kurzhaar", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "puenktchen.jpg");
        addAnimal("Rocko", "Europ" +ae+ "isch Kurzhaar", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.SMALL, mainpath + "rocko.jpg");
        addAnimal("Karlchen", "Europ" +ae+ "isch Kurzhaar", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath);
        addAnimal("Foxi", euroKurz, AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.MUCH, AnimalGardenSpace.NONE, mainpath + "foxi.jpg");
        addAnimal("Casper", euroKurz, AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.MUCH, AnimalGardenSpace.NONE, mainpath + "casper.jpg");
        addAnimal("General", "Orientalisch Kurzhaar Mischling", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "general.jpg");
        addAnimal("Mauli", euroKurz, AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
        		AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.HUGE, mainpath + "mauli.jpg");
        addAnimal("Wilma", euroKurz, AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "wilma.jpg");
        addAnimal("Katharina", euroKurz, AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "katharina.jpg");
        addAnimal("Ronja", euroKurz, AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "ronja.jpg");
        addAnimal("Mary", euroKurz, AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "mary.jpg");
        addAnimal("Mira", euroKurz, AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
        		AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "mira.jpg");
        addAnimal("Graf Zahl", euroKurz, AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "graf_zahl.jpg");
        addAnimal("Buddy", euroKurz, AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.SMALL, mainpath + "buddy.jpg");
        

        // Bunnies
        addAnimal("Peach", "Kaninchen", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "peach.jpg");
        addAnimal("Chipman", "L" +oe+ "wenkopf-Kaninchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "chipman.jpg");

        // Birds
        addAnimal("Jewels und Yellow", "Kanarienvogel", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "jewels-yellow.jpg");
        addAnimal("Vasco", "Graupapagei", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.HUGE,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "vasco.jpg");
        addAnimal("H" +ae+ "hne", "Leghorn (Hahn)", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "haehne.jpg");
        
        // Guinea Pig
        addAnimal("Rambo", "Meerschweinchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.GUINEA_PIG, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "rambo.jpg");

        // Mouses
        addAnimal("Perdita", "Chinchilla", AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "perdita.jpg");
        addAnimal("Bibi und Tina", "Chinchilla", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "bibi-tina.jpg");

        // Reptiles
        addAnimal("Sting", "Berberskink", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "sting.jpg");
        addAnimal("Noah", "Kornnatter", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.HUGE, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "noah.jpg");
        addAnimal("Horst", "Bartagamme", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "horst.jpg");
        addAnimal("Chinesische Dreikielschildkr" +oe+ "ten", "Mauremys reevesii", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.REPTILE, AnimalSize.HUGE, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.MEDIUM, mainpath + "chin-schildkroeten.jpg");
        addAnimal("Yeti", "Menschenaffe", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.HUGE,
        		AnimalType.REPTILE, AnimalSize.HUGE, AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.HUGE, mainpath + "yeti.jpg");
        
    }

    /**
     * Adds a question with answers to the database.
     *
     * @param questionText  Question text.
     * @param answerContent Content of answers.
     */
    void addQuestionWithAnswers(String questionText, AnswerType answerType, AnswerContent... answerContent) {
        QuestionEntity question = new QuestionEntity(questionSortOrder++, questionText, answerType);
        question = questionDAO.save(question);
        question.answers = new ArrayList<>(answerContent.length);

        int answerSortOrder = 1;
        for (AnswerContent currentAnswerContent : answerContent) {
            AnswerEntity answer = new AnswerEntity(answerSortOrder++, currentAnswerContent.text, question,
                    currentAnswerContent.animalType, currentAnswerContent.animalSize, currentAnswerContent.cost, currentAnswerContent.needCare, currentAnswerContent.garden);
            question.answers.add(answerDAO.save(answer));
        }
    }

    void addAnimal(String name, String race, AnimalSex sex, AnimalAge age, AnimalGardenSpace requiredSpace,
                           AnimalType animalType, AnimalSize size, AnimalCost cost, AnimalCareTyp needCare, AnimalGardenSpace garden, String picturePath) {
        animalDAO.save(new AnimalEntity(name, race, sex, age, requiredSpace, animalType, size, cost, needCare, garden, picturePath));
    }
}

class AnswerContent {

    String text;

    Set<AnimalType> animalType;

    Set<AnimalSize> animalSize;

    Set<AnimalCost> cost;

    Set<AnimalCareTyp> needCare;

    Set<AnimalGardenSpace> garden;

    AnswerContent(String answerText, Set<AnimalType> animalType, Set<AnimalSize> animalSize, Set<AnimalCost> cost, Set<AnimalCareTyp> needCare, Set<AnimalGardenSpace> garden) {
        this.text = answerText;
        this.animalType = animalType;
        this.animalSize = animalSize;
        this.cost = cost;
        this.needCare = needCare;
        this.garden = garden;
    }
}
