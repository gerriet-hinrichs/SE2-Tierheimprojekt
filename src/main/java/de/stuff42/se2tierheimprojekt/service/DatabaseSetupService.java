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

        // Questions & Answers
        addQuestionWithAnswers("How many square meters are available for animal husbandry?",
                new AnswerContent("<35",
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
                new AnswerContent(">55",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Can the animal be kept in the garden?",
                new AnswerContent("Yes",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("No",
                        null,
                        null,
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.SMALL, AnimalGardenSpace.MEDIUM, AnimalGardenSpace.HUGE)))
        );
        addQuestionWithAnswers("In which environment is the animal kept?",
                new AnswerContent("Country / village",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Residential area without main roads in direct vicinity",
                        null,
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("On a main road or in the city center",
                        new HashSet<>(Collections.singletonList(AnimalType.DOG)),
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("How many hours do they have an average time per day for the animal?",
                new AnswerContent("<1",
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
                new AnswerContent("All day",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("Should the animal also be supplied by children?",
                new AnswerContent("Yes",
                        new HashSet<>(Arrays.asList(AnimalType.DOG, AnimalType.BIRD, AnimalType.BUNNY)),
                        null,
                        null,
                        new HashSet<>(Arrays.asList(AnimalCareTyp.SOME, AnimalCareTyp.MUCH)),
                        null),
                new AnswerContent("No",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        addQuestionWithAnswers("How much should the monthly cost be? (Without basic equipment)",
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

        // Dogs
        addAnimal("Didi", "Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalSpace.SMALL,
                AnimalType.DOG, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Slushi", "Jack-Russell-Terrier-Mischling", AnimalSex.MALE, AnimalAge.YOUNG, AnimalSpace.SMALL,
                AnimalType.DOG, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Sandor", "Malinois", AnimalSex.MALE, AnimalAge.YOUNG, AnimalSpace.HUGE,
                AnimalType.DOG, AnimalSize.HUGE, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Thelma", "Mischling", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalSpace.MEDIUM,
                AnimalType.DOG, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.HUGE);
        addAnimal("Anuk", "Thai Ridgeback", AnimalSex.FEMALE, AnimalAge.OLD, AnimalSpace.MEDIUM,
                AnimalType.DOG, AnimalSize.MEDIUM, AnimalCost.EXPENSIVE, AnimalCareTyp.MUCH, AnimalGardenSpace.NONE);

        // Cats
        addAnimal("Tobi", "Europaeisch Kurzhaat", AnimalSex.MALE, AnimalAge.OLD, AnimalSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Mia", "Siam", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Lisa", "Kartaeuser-Mischling", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Jenny", "Europaeisch Kurzhaat", AnimalSex.MALE, AnimalAge.OLD, AnimalSpace.SMALL,
                AnimalType.CAT, AnimalSize.SMALL, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);

        // Bunnies
        addAnimal("Peach", "Kaninchen", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalSpace.SMALL,
                AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Chipman", "Loewenkopf-Kaninchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalSpace.SMALL,
                AnimalType.BUNNY, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);

        // Birds
        addAnimal("Jewels und Yellow", "Kanarienvogel", AnimalSex.FEMALE, AnimalAge.MATURE, AnimalSpace.MEDIUM,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Vasco", "Graupapagei", AnimalSex.MALE, AnimalAge.OLD, AnimalSpace.HUGE,
                AnimalType.BIRD, AnimalSize.SMALL, AnimalCost.EXPENSIVE, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);

        // Guinea Pig
        addAnimal("Rambo", "Meerschweinchen", AnimalSex.MALE, AnimalAge.MATURE, AnimalSpace.SMALL,
                AnimalType.GUINEA_PIG, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);

        // Mouses
        addAnimal("Perdita", "Chinchilla", AnimalSex.FEMALE, AnimalAge.OLD, AnimalSpace.SMALL,
                AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Bibi und Tina", "Chinchilla", AnimalSex.FEMALE, AnimalAge.YOUNG, AnimalSpace.SMALL,
                AnimalType.MOUSE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);

        // Reptiles
        addAnimal("Huenne", "Leghorn (Hahn)", AnimalSex.MALE, AnimalAge.MATURE, AnimalSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Sting", "Echse", AnimalSex.MALE, AnimalAge.OLD, AnimalSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Noah", "Kornnatter", AnimalSex.MALE, AnimalAge.MATURE, AnimalSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.HUGE, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        addAnimal("Horst", "Bartagamme", AnimalSex.MALE, AnimalAge.MATURE, AnimalSpace.SMALL,
                AnimalType.REPTILE, AnimalSize.SMALL, AnimalCost.CHEAP, AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
    }

    /**
     * Adds a question with answers to the database.
     *
     * @param questionText  Question text.
     * @param answerContent Content of answers.
     */
    void addQuestionWithAnswers(String questionText, AnswerContent... answerContent) {
        QuestionEntity question = new QuestionEntity(questionSortOrder++, questionText);
        question = questionDAO.save(question);
        question.answers = new ArrayList<>(answerContent.length);

        int answerSortOrder = 1;
        for (AnswerContent currentAnswerContent : answerContent) {
            AnswerEntity answer = new AnswerEntity(answerSortOrder++, currentAnswerContent.text, question,
                    currentAnswerContent.animalType, currentAnswerContent.animalSize, currentAnswerContent.cost, currentAnswerContent.needCare, currentAnswerContent.garden);
            question.answers.add(answerDAO.save(answer));
        }
    }

    void addAnimal(String name, String race, AnimalSex sex, AnimalAge age, AnimalSpace requiredSpace,
                           AnimalType animalType, AnimalSize size, AnimalCost cost, AnimalCareTyp needCare, AnimalGardenSpace garden) {
        animalDAO.save(new AnimalEntity(name, race, sex, age, requiredSpace, animalType, size, cost, needCare, garden));
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
