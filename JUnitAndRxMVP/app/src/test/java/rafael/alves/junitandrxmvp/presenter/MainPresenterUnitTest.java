package rafael.alves.junitandrxmvp.presenter;

import org.junit.Test;

import rafael.alves.junitandrxmvp.model.PokemonList;
import rafael.alves.junitandrxmvp.model.TestResponseEnum;
import rafael.alves.junitandrxmvp.repository.listeners.PokemonListListener;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainPresenterUnitTest extends BaseUnitTest {

    private final String TAG = MainPresenterUnitTest.class.getSimpleName();

    @Test
    public void mainPresenter_notNull() throws Exception {
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.loadPokemonList(0, new PokemonListListener() {
            @Override
            public void onPokemonListLoad(PokemonList pokemonList) {
                checkPokemonListTests(pokemonList);
            }

            @Override
            public void onRequestStart() {

            }

            @Override
            public void onRequestFinish() {

            }

            @Override
            public void onError(Throwable throwable) {
                checkError(throwable);
            }
        });
    }

    private void checkPokemonListTests(PokemonList pokemonList) {
        // Assert that list is different from null
        assertFalse(pokemonList == null);
        super.printMessage(TAG,  "mainPresenter_notNull", "pokemonList not null", TestResponseEnum.PASSED);

        // Assert that there is at least one item in list
        int expectedMinimumItemsInList = 1;
        int channelsInList = pokemonList.results.size();
        assertTrue(channelsInList >= expectedMinimumItemsInList);
        super.printMessage(TAG,  "mainPresenter_notNull", String.format("pokemonList.results.size() > %d", expectedMinimumItemsInList), TestResponseEnum.PASSED);
    }

    private void checkError(Throwable error) {
        super.printMessage(TAG,  "mainPresenter_notNull", error.getMessage(), TestResponseEnum.FAILED);
        assertTrue(false);
    }
}
