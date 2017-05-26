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
        addQuestionWithAnswers(1,"How many square meters are available for animal husbandry?",
                "<35", "35-55", ">55");
        addQuestionWithAnswers(1,"Can the animal be kept in the garden?",
                "Yes", "No");
        addQuestionWithAnswers(1,"In which environment is the animal kept?",
                "Country / village", "Residential area without main roads in direct vicinity", "On a main road or in the city center");
        addQuestionWithAnswers(1,"How many hours do they have an average time per day for the animal?",
                "<1", "1-4", "5-8", "All day");
        addQuestionWithAnswers(1,"Should the animal also be supplied by children?",
                "Yes", "No");
        addQuestionWithAnswers(1,"How much should the monthly cost be? (Without basic equipment)",
                "20-30", "30-60", "60-80");

        // Dogs
        addAnimal("Didi","Mischling","Maennlich","Jung","Klein",
                  "dog","medium","expensive",false,false);
        addAnimal("Slushi","Jack-Russell-Terrier-Mischling","Maennlich","Jung","Klein",
                "dog","small","medium",false,false);
        addAnimal("Sandor","Malinois","Maennlich","Jung","Gross",
                "dog","huge","expensive",false,false);
        addAnimal("Thelma","Mischling","Weiblich","Ausgewachsen","Mittel",
                "dog","medium","expensive",false,true);
        addAnimal("Anuk","Thai Ridgeback","Weiblich","Alt","Mittel",
                "dog","medium","expensive",true,false);

        // Cats
        addAnimal("Tobi","Europaeisch Kurzhaat","Maennlich","Alt","Klein",
                "cat","small","medium",false,false);
        addAnimal("Mia","Siam","Weiblich","Jung","Klein",
                "cat","small","medium",false,false);
        addAnimal("Lisa","Kartaeuser-Mischling","Weiblich","Ausgewachsen","Klein",
                "cat","small","medium",false,false);
        addAnimal("Jenny","Europaeisch Kurzhaat","Maennlich","Alt","Klein",
                "cat","small","medium",false,false);

        // Bunnies
        addAnimal("Peach","Kaninchen","Weiblich","Jung","Klein",
                "bunny","small","cheap",false,false);
        addAnimal("Chipman","Loewenkopf-Kaninchen","Maennlich","Ausgewachsen","Klein",
                "bunny","small","cheap",false,false);

        // Birds
        addAnimal("Jewels und Yellow","Kanarienvogel","Weiblich","Ausgewachsen","Mittel",
                "bird","small","cheap",false,false);
        addAnimal("Vasco","Graupapagei","Maennlich","Alt","Gross",
                "bird","small","expensive",false,false);

        // Guinea Pig
        addAnimal("Rambo","Meerschweinchen","Maennlich","Ausgewachsen","Klein",
                "guineaPig","small","cheap",false,false);

        // Mouses
        addAnimal("Perdita","Chinchilla","Weiblich","Alt","Klein",
                "mouse","small","cheap",false,false);
        addAnimal("Bibi und Tina","Chinchilla","Weiblich","Jung","Klein",
                "mouse","small","cheap",false,false);

        // Reptiles
        addAnimal("Huenne","Leghorn (Hahn)","Maennlich","Ausgewachsen","Klein",
                "reptile","small","cheap",false,false);
        addAnimal("Sting","Echse","Maennlich","Alt","Klein",
                "reptile","small","cheap",false,false);
        addAnimal("Noah","Kornnatter","Maennlich","Ausgewachsen","Klein",
                "reptile","huge","medium",false,false);
        addAnimal("Horst","Bartagamme","Maennlich","Ausgewachsen","Klein",
                "reptile","small","cheap",false,false);
    }

    /**
     * Adds a question with answers to the database.
     *
     * @param questionSortOrder Question sort order.
     * @param questionText      Question text.
     * @param answers           Ordered answers.
     */
    private void addQuestionWithAnswers(int questionSortOrder, String questionText, String... answers) {
        QuestionEntity question = new QuestionEntity(questionSortOrder, questionText);
        questionDAO.save(question);

        int answerSortOrder = 1;
        for (String answerText : answers) {
            AnswerEntity answer = new AnswerEntity(answerSortOrder++, answerText, question);
            answerDAO.save(answer);
        }
    }

    private void addAnimal( String name, String race, String sex, String age, String requiredSpace,
                            String animalType, String size, String cost, Boolean needCare, Boolean garden) {
        animalDAO.save(new AnimalEntity(name, race, sex, age, requiredSpace, animalType, size ,cost, needCare, garden));
    }
}
