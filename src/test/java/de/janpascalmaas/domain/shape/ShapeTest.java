package de.janpascalmaas.domain.shape;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShapeTest {

    private final Shape rock = new Shape(ShapeType.ROCK);
    private final Shape paper = new Shape(ShapeType.PAPER);
    private final Shape scissors = new Shape(ShapeType.SCISSORS);

    @Test
    void shapeOfTypeRockBeatsScissors() {
        assertThat(rock.beats(scissors))
                .as("Rock should beat Scissors")
                .isTrue();;
    }

    @Test
    void shapeOfTypeRockDoesNotBeatPaper() {
        assertThat(rock.beats(paper))
                .as("Rock should not beat Paper")
                .isFalse();
    }

    @Test
    void shapeOfTypeRockDoesNotBeatRock() {
        assertThat(rock.beats(rock))
                .as("Rock should not beat Rock")
                .isFalse();
    }

    @Test
    void shapeOfTypePaperBeatsRock() {
        assertThat(paper.beats(rock))
                .as("Paper should beat Rock")
                .isTrue();
    }

    @Test
    void shapeOfTypePaperDoesNotBeatScissors() {
        assertThat(paper.beats(scissors))
                .as("Paper should not beat Scissors")
                .isFalse();
    }

    @Test
    void shapeOfTypePaperDoesNotBeatPaper() {
        assertThat(paper.beats(paper))
                .as("Paper should not beat Paper")
                .isFalse();
    }

    @Test
    void shapeOfTypeScissorsBeatsPaper() {
        assertThat(scissors.beats(paper))
                .as("Scissors should beat Paper")
                .isTrue();
    }

    @Test
    void shapeOfTypeScissorsDoesNotBeatRock() {
        assertThat(scissors.beats(rock))
                .as("Scissors should not beat Rock")
                .isFalse();
    }

    @Test
    void shapeOfTypeScissorsDoesNotBeatScissors() {
        assertThat(scissors.beats(scissors))
                .as("Scissors should not beat Scissors")
                .isFalse();
    }

}