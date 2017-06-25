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

import de.stuff42.se2tierheimprojekt.model.AnswerContent;
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

        final String mainpath = "/static/images/shelter_animals/";

        // Questions & Answers
        addQuestionWithAnswers("Wieviele Quadratmeter stehen ungefähr für die Tierhaltung zur Verfügung?", AnswerType.RADIO_BUTTON,
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
        addQuestionWithAnswers("Steht ein Garten für die Tierhaltung zur Verfügung?", AnswerType.RADIO_BUTTON,
                new AnswerContent("Nein",
                        null,
                        null,
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.SMALL, AnimalGardenSpace.MEDIUM, AnimalGardenSpace.HUGE))),
                new AnswerContent("Ja, bis ca. 25 Quadratmeter",
                        null,
                        null,
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.MEDIUM, AnimalGardenSpace.HUGE))),
                new AnswerContent("Ja, ca. 25 bis 50 Quadratmeter",
                        null,
                        null,
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.HUGE))),
                new AnswerContent("Ja, größer 50 Quadratmeter",
                        null,
                        null,
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.HUGE)))
        );
        addQuestionWithAnswers("In welcher Umgebung wird das Tier gehalten?", AnswerType.RADIO_BUTTON,
                new AnswerContent("Land / Dorf",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Wohngebiet ohne Hauptstraße in direkter Umgebung",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("An einer Hauptstraße oder im Stadtkern",
                        new HashSet<>(Collections.singletonList(AnimalType.DOG)),
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Wieviele Stunden hast du durchschnittlich täglich Zeit für das Tier?", AnswerType.RADIO_BUTTON,
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
        addQuestionWithAnswers("Soll das Tier auch von Kindern geplegt werden?", AnswerType.RADIO_BUTTON,
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
        addQuestionWithAnswers("Wie hoch sollten ungefähr die monatlichen Kosten sein? (Ohne Grundausstattung)", AnswerType.RADIO_BUTTON,
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
        addQuestionWithAnswers("Sollen Tierarten ausgeschlossen werden?", AnswerType.CHECKBOX,
                new AnswerContent("Keine ausschließen",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Hund",
                        new HashSet<>(Arrays.asList(AnimalType.DOG)),
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Katze",
                        new HashSet<>(Arrays.asList(AnimalType.CAT)),
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Vogel",
                        new HashSet<>(Arrays.asList(AnimalType.BIRD)),
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Fisch",
                        new HashSet<>(Arrays.asList(AnimalType.FISH)),
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Reptil",
                        new HashSet<>(Arrays.asList(AnimalType.REPTILE)),
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Hamster",
                        new HashSet<>(Arrays.asList(AnimalType.HAMSTER)),
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Kaninchen",
                		new HashSet<>(Arrays.asList(AnimalType.BUNNY)),
                		null,
                		null, 
                		null, 
                		null),
                new AnswerContent("Meerschweinchen",
                		new HashSet<>(Arrays.asList(AnimalType.GUINEA_PIG)),
                		null,
                		null, 
                		null, 
                		null),
                new AnswerContent("Maus",
                		new HashSet<>(Arrays.asList(AnimalType.MOUSE)),
                		null,
                		null, 
                		null, 
                		null)
        );
        addQuestionWithAnswers("Sind tagsüber laute Geräusche für Mitbewohner und Nachbarn zumutbar?", AnswerType.RADIO_BUTTON,
        		new AnswerContent("Ja",
        				null, 
        				null, 
        				null, 
        				null, 
        				null),
        		new AnswerContent("Nur gelegendlich", 
        				new HashSet<>(Arrays.asList(AnimalType.BIRD)), 
        				null, 
        				null, 
        				null, 
        				null),
        		new AnswerContent("Nein", 
        				new HashSet<>(Arrays.asList(AnimalType.BIRD, AnimalType.DOG)), 
        				null, 
        				null, 
        				null, 
        				null)
        );
        addQuestionWithAnswers("Hast du Erfahrungen mit der Haltung von Tieren?", AnswerType.RADIO_BUTTON, 
        		new AnswerContent("Ja", 
        				null, 
        				null, 
        				null, 
        				null, 
        				null),
        		new AnswerContent("Etwas", 
        				null, 
        				null, 
        				null, 
        				new HashSet<>(Arrays.asList(AnimalCareTyp.MUCH)), 
        				null),
        		new AnswerContent("Nein",
        				null, 
        				null, 
        				null, 
        				new HashSet<>(Arrays.asList(AnimalCareTyp.MUCH, AnimalCareTyp.SOME)), 
        				null)
        );
        addQuestionWithAnswers("Sollen Tiere einer gewissen Größe ausgeschlossen werden?", AnswerType.CHECKBOX, 
        		new AnswerContent("Keine ausschließen",
                        null,
                        null,
                        null,
                        null,
                        null),
        		new AnswerContent("Keine großen Tiere", 
        				null, 
        				new HashSet<>(Arrays.asList(AnimalSize.HUGE)), 
        				null, 
        				null, 
        				null),
        		new AnswerContent("Keine mittelgroßen und großen Tiere", 
        				null, 
        				new HashSet<>(Arrays.asList(AnimalSize.HUGE, AnimalSize.MEDIUM)), 
        				null, 
        				null, 
        				null),
        		new AnswerContent("Keine kleinen und mittelgroßen Tiere", 
        				null, 
        				new HashSet<>(Arrays.asList(AnimalSize.MEDIUM, AnimalSize.SMALL)), 
        				null, 
        				null, 
        				null)
        );
        addQuestionWithAnswers("Falls du zur Miete wohnst, hast du eine Genehmigung zur Hundehaltung?", AnswerType.RADIO_BUTTON,
        		new AnswerContent("Ich wohne nicht zur Miete.",
        				null,
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Ja oder beantrage ich.",
        				null,
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Nein",
        				new HashSet<>(Arrays.asList(AnimalType.DOG)),
        				null,
        				null,
        				null,
        				null)
        );
        addQuestionWithAnswers("Wer pflegt das Tier falls du im Urlaub bist?", AnswerType.RADIO_BUTTON,
        		new AnswerContent("Steht noch nicht fest",
        				null,
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Verwandte, Bekannte oder Nachbarn",
        				null,
        				null,
        				null,
        				new HashSet<>(Arrays.asList(AnimalCareTyp.MUCH)),
        				null),
        		new AnswerContent("Eine Tierpension",
        				null,
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Das Tier kommt mit in den Urlaub",
        				new HashSet<>(Arrays.asList(AnimalType.FISH, AnimalType.BIRD)),
        				null,
        				null,
        				null,
        				null)
        );
        addQuestionWithAnswers("Soll das Tier einen Zweck erfüllen?", AnswerType.RADIO_BUTTON,
        		new AnswerContent("Kuscheln, spielen und toben",
        				new HashSet<>(Arrays.asList(AnimalType.FISH, AnimalType.BIRD)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Oft anschauen, füttern",
        				new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT)),
        				null,
        				null,
        				null,
        				null)
        );
        addQuestionWithAnswers("Wie alt sollte das Tier werden?", AnswerType.RADIO_BUTTON,
        		new AnswerContent("Ungefähr 2 bis 7 Jahre",
        				new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.CAT, AnimalType.BIRD, AnimalType.REPTILE)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Ungefähr 8 bis 15 Jahre",
        				new HashSet<>(Arrays.asList(AnimalType.BUNNY, AnimalType.GUINEA_PIG, AnimalType.MOUSE)),
        				null,
        				null,
        				null,
        				null),
        		new AnswerContent("Älter",
        				new HashSet<>(Arrays.asList(AnimalType.BUNNY, AnimalType.GUINEA_PIG, AnimalType.MOUSE, AnimalType.CAT, AnimalType.DOG)),
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
        addAnimal("Pancho", "Schäferhund-Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.HUGE,
                AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "pancho.jpg");
        addAnimal("Schnipsel", "Staffordshire-Bullterrier-Mischling", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.HUGE, AnimalType.DOG, AnimalSize.MEDIUM,
                AnimalCost.MEDIUM, AnimalCareTyp.MUCH, AnimalGardenSpace.MEDIUM, mainpath + "schnipsel.jpg");


        // Cats
        addAnimal("Tobi", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "tobi.jpg");
        addAnimal("Mia", "Siam", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "mia.jpg");
        addAnimal("Lisa", "Karthäuser-Mischling", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "lisa.jpg");
        addAnimal("Jerry", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "jerry.jpg");
        addAnimal("Paul", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "paul.jpg");
        addAnimal("Sir Simon", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.HUGE, mainpath + "sir_simon.jpg");
        addAnimal("Pünktchen", "Europäisch Kurzhaar", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "puenktchen.jpg");
        addAnimal("Rocko", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.SMALL, mainpath + "rocko.jpg");
        addAnimal("Karlchen", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "karlchen.jpg");
        addAnimal("Foxi", "Europäisch Kurzhaar", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.MUCH, AnimalGardenSpace.NONE, mainpath + "foxi.jpg");
        addAnimal("Casper", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.MUCH, AnimalGardenSpace.NONE, mainpath + "casper.jpg");
        addAnimal("General", "Orientalisch Kurzhaar Mischling", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "general.jpg");
        addAnimal("Mauli", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.HUGE, mainpath + "mauli.jpg");
        addAnimal("Wilma", "Europäisch Kurzhaar", AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "wilma.jpg");
        addAnimal("Katharina", "Europäisch Kurzhaar", AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "katharina.jpg");
        addAnimal("Ronja", "Europäisch Kurzhaar", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "ronja.jpg");
        addAnimal("Mary", "Europäisch Kurzhaar", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "mary.jpg");
        addAnimal("Mira", "Europäisch Kurzhaar", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "mira.jpg");
        addAnimal("Graf Zahl", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "graf_zahl.jpg");
        addAnimal("Buddy", "Europäisch Kurzhaar", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.SMALL, mainpath + "buddy.jpg");


        // Bunnies
        addAnimal("Peach", "Kaninchen", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "peach.jpg");
        addAnimal("Chipman", "Löwenkopf-Kaninchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "chipman.jpg");
        addAnimal("Olaf", "Kaninchen", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
        		AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "olaf.jpg");
        addAnimal("Bacardi", "Löwenkopf-Kaninchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
        		AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.SMALL, mainpath + "bacardi.jpg");
        addAnimal("Daisy", "Widderkaninchen", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
        		AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.SMALL, mainpath + "daisy.jpg");
        addAnimal("Motte", "Zwergkaninchen", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
        		AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.SMALL, mainpath + "motte.jpg");
        addAnimal("Peter", "Zwergkaninchen", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
        		AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.SMALL, mainpath + "peter.jpg");
        addAnimal("Sleepy und Smudo", "Angora-Kaninchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.MEDIUM, mainpath + "sleepy_smudo.jpg");
        

        // Birds
        addAnimal("Jewels und Yellow", "Kanarienvogel", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "jewels-yellow.jpg");
        addAnimal("Vasco", "Graupapagei", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.HUGE,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "vasco.jpg");
        addAnimal("Hähne", "Leghorn (Hahn)", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "haehne.jpg");
        addAnimal("Mona und Lisa", "Diamanttäubchen", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "mona_lisa.jpg");
        addAnimal("Max", "Halsbandsittich", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.HUGE,
        		AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "max.jpg");
        addAnimal("Jamie", "Prachtrosella", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.BIRD, AnimalSize.MEDIUM, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "jamie.jpg");
        addAnimal("Fiete", "Prachtrosella", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.BIRD, AnimalSize.MEDIUM, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "fiete.jpg");
        addAnimal("Elvis", "Königssittich", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.HUGE,
        		AnimalType.BIRD, AnimalSize.MEDIUM, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "elvis.jpg");
        
        
        // Guinea Pig
        addAnimal("Rambo", "Meerschweinchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.GUINEA_PIG, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "rambo.jpg");
        addAnimal("Maja", "Meerschweinchen", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
        		AnimalType.GUINEA_PIG, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.SMALL, mainpath + "maja.jpg");
        

        // Mouses
        addAnimal("Perdita", "Chinchilla", AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "perdita.jpg");
        addAnimal("Bibi und Tina", "Chinchilla", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
                AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "bibi-tina.jpg");
        addAnimal("Angel Family", "Farbratten", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalGardenSpace.MEDIUM,
        		AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "angel_family.jpg");
        addAnimal("Selma und Luise", "Chinchilla", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
        		AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "selma_luise.jpg");
        addAnimal("Hanni und Nanni", "Chinchilla", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
        		AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "hanni_nanni.jpg");
        

        // Reptiles
        addAnimal("Sting", "Berberskink", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "sting.jpg");
        addAnimal("Noah", "Kornnatter", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.HUGE, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "noah.jpg");
        addAnimal("Horst", "Bartagamme", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "horst.jpg");
        addAnimal("Chinesische Dreikielschildkröten", "Mauremys reevesii", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.REPTILE, AnimalSize.HUGE, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.MEDIUM, mainpath + "chin-schildkroeten.jpg");
        addAnimal("Pixi", "Leopardgecko", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
        		AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "pixi.jpg");
        addAnimal("Chuck und Elvis", "Kornnattern", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.SMALL,
        		AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, mainpath + "chuck_elvis.jpg");
        addAnimal("Dieter", "Bartagame", AnimalSex.MALE, AnimalAge.YOUNG, AnimalGardenSpace.SMALL,
        		AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "dieter.jpg");
        addAnimal("Elfriede", "Königspython", AnimalSex.FEMALE, AnimalAge.OLD, AnimalGardenSpace.HUGE,
        		AnimalType.REPTILE, AnimalSize.HUGE, AnimalCost.MEDIUM, AnimalCareTyp.SOME, AnimalGardenSpace.NONE, mainpath + "elfriede.jpg");
        addAnimal("Paul", "Maurische Landschildkröte", AnimalSex.MALE, AnimalAge.OLD, AnimalGardenSpace.MEDIUM,
        		AnimalType.REPTILE, AnimalSize.MEDIUM, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.MEDIUM, mainpath + "paul2.jpg");
        
        
        // Others
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

