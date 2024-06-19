package com.mygdx.game.utility;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.PickLevelScreen;
import com.mygdx.game.screens.SetGameModeScreen;

import java.util.ArrayList;
import java.util.Set;

public class GameSession {
    long nextTrashSpawnTime;
    long nextBoostSpawnTime;
    long nextBossSpawnTime;
    MyGdxGame myGdxGame;
    long sessionStartTime;
    long pauseStartTime;
    private int score;
    int destructedTrashNumber, destructedSuperTrashNumber, destructedBossNumber;
    public GameState state;

    public void startGame() {
        state = GameState.PLAYING;
        sessionStartTime = TimeUtils.millis();
        GameScreen.bossCount = 0;
        destructedTrashNumber = 0;
        destructedSuperTrashNumber = 0;
        destructedBossNumber = 0;
        nextTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getPeriodCoolDown());
        nextBoostSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_BOOST_APPEARANCE_COOL_DOWN
                * getPeriodCoolDown());
        nextBossSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_BOSS_APPEARANCE_COOL_DOWN
            * getPeriodCoolDown());
    }
    public void pauseGame() {
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }
    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

    public void endGame() {
        updateScore();
        state = GameState.ENDED;
        if (!SetGameModeScreen.gameMode) {
            ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
            if (recordsTable == null) {
                recordsTable = new ArrayList<>();
            }
            int foundIdx = 0;
            for (; foundIdx < recordsTable.size(); foundIdx++) {
                if (recordsTable.get(foundIdx) < getScore()) break;
            }
            recordsTable.add(foundIdx, getScore());
            MemoryManager.saveTableOfRecords(recordsTable);
        }
    }


    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getPeriodCoolDown());
            return true;
        }
        return false;
    }

    public boolean shouldSpawnBoost() {
        if (nextBoostSpawnTime <= TimeUtils.millis()) {
            nextBoostSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_BOOST_APPEARANCE_COOL_DOWN
                    * getPeriodCoolDown());
            return true;
        }
        return false;
    }

    public boolean shouldSpawnBoss() {
        if (SetGameModeScreen.gameMode) {
            if (nextBossSpawnTime <= TimeUtils.millis()) {
                nextBossSpawnTime = TimeUtils.millis() +
                        (long) (GameSettings.STARTING_BOSS_APPEARANCE_COOL_DOWN *
                                PickLevelScreen.levelNumber * getPeriodCoolDown());
                return true;
            }
        }
        return false;
    }

    private float getPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime) / 1000);
    }
    public void destructionRegistration() {
        destructedTrashNumber += 1;
    }
    public void SuperDestructionRegistration() {
        destructedSuperTrashNumber += 1;
    }
    public void BossDestructionRegistration() {
        destructedBossNumber += 1;
    }
    public void updateScore() {
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100 +
                destructedSuperTrashNumber * 300;
        if (SetGameModeScreen.gameMode) {
            score += destructedBossNumber * 3000;
        }
    }
    public int getScore() {
        return score;
    }
}
