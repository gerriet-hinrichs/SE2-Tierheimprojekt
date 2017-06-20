package de.stuff42.se2tierheimprojekt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.stuff42.se2tierheimprojekt.data.*;
import de.stuff42.se2tierheimprojekt.entity.AnswerEntity;

public class EvaluationResult {

    public List<AnimalType> animalTypes = new ArrayList<>();

    public List<AnimalCost> animalCosts = new ArrayList<>();

    public List<AnimalSize> animalSizes = new ArrayList<>();

    public List<AnimalGardenSpace> animalGardenSpaces = new ArrayList<>();

    public List<AnimalCareTyp> animalCareTypes = new ArrayList<>();

    public EvaluationResult(boolean fillWithData) {
        if (fillWithData) {

            // the list returned from Arrays.asList is not modifiable, so we need this here
            animalTypes.addAll(Arrays.asList(AnimalType.values()));
            animalCosts.addAll(Arrays.asList(AnimalCost.values()));
            animalSizes.addAll(Arrays.asList(AnimalSize.values()));
            animalGardenSpaces.addAll(Arrays.asList(AnimalGardenSpace.values()));
            animalCareTypes.addAll(Arrays.asList(AnimalCareTyp.values()));
        }
    }

    public void add(AnswerEntity answer) {
        if (answer.animalType != null) {
            animalTypes.addAll(answer.animalType);
        }

        if (answer.cost != null) {
            animalCosts.addAll(answer.cost);
        }

        if (answer.animalSize != null) {
            animalSizes.addAll(answer.animalSize);
        }

        if (answer.garden != null) {
            animalGardenSpaces.addAll(answer.garden);
        }

        if (answer.needCare != null) {
            animalCareTypes.addAll(answer.needCare);
        }
    }

    public void intersect(AnswerEntity answer) {
        if (answer.animalType != null) {
            animalTypes.retainAll(answer.animalType);
        }

        if (answer.cost != null) {
            animalCosts.retainAll(answer.cost);
        }

        if (answer.animalSize != null) {
            animalSizes.retainAll(answer.animalSize);
        }

        if (answer.garden != null) {
            animalGardenSpaces.retainAll(answer.garden);
        }

        if (answer.needCare != null) {
            animalCareTypes.retainAll(answer.needCare);
        }
    }

    public void remove(AnswerEntity answer) {
        if (answer.animalType != null) {
            animalTypes.removeAll(answer.animalType);
        }

        if (answer.cost != null) {
            animalCosts.removeAll(answer.cost);
        }

        if (answer.animalSize != null) {
            animalSizes.removeAll(answer.animalSize);
        }

        if (answer.garden != null) {
            animalGardenSpaces.removeAll(answer.garden);
        }

        if (answer.needCare != null) {
            animalCareTypes.removeAll(answer.needCare);
        }
    }

    public void remove(EvaluationResult other) {
        animalTypes.removeAll(other.animalTypes);
        animalCosts.removeAll(other.animalCosts);
        animalSizes.removeAll(other.animalSizes);
        animalGardenSpaces.removeAll(other.animalGardenSpaces);
        animalCareTypes.removeAll(other.animalCareTypes);
    }

    public boolean isEmpty() {
        return animalTypes.isEmpty()
                || animalCosts.isEmpty()
                || animalSizes.isEmpty()
                || animalGardenSpaces.isEmpty()
                || animalCareTypes.isEmpty();
    }
}
