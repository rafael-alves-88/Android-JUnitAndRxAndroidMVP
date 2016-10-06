package rafael.alves.junitandrxmvp.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rafael.alves.junitandrxmvp.R;
import rafael.alves.junitandrxmvp.model.PokemonList;
import rafael.alves.junitandrxmvp.model.Result;
import rafael.alves.junitandrxmvp.presenter.MainPresenter;
import rafael.alves.junitandrxmvp.repository.listeners.PokemonListListener;
import rafael.alves.junitandrxmvp.view.adapters.PokemonAdapter;

public class MainActivity extends AppCompatActivity {

    //region [ ButterKnife ]
    @Bind(R.id.rvPokemonList) RecyclerView rvPokemonList;
    @Bind(R.id.rlLoading) RelativeLayout rlLoading;
    @Bind(R.id.rlSmallLoading) RelativeLayout rlSmallLoading;
    //endregion

    //region [ Private Members ]
    private final String TAG = rafael.alves.junitandrxmvp.view.ui.MainActivity.class.getSimpleName();
    private PokemonAdapter mPokemonAdapter;
    private MainPresenter mMainPresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private int mNextOffset = 0;
    private boolean isFirstLoad = true;
    //endregion

    //region [ LifeCycle ]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Presenter
        mMainPresenter = new MainPresenter();

        // Layout Manager
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvPokemonList.setLayoutManager(mLinearLayoutManager);

        // Adapter
        List<Result> dummyResult = new ArrayList<>();
        mPokemonAdapter = new PokemonAdapter(dummyResult, new PokemonAdapter.OnPokemonClickListener() {
            @Override
            public void onPokemonClick(Result pokemonFromList) {
                Toast.makeText(MainActivity.this, pokemonFromList.name, Toast.LENGTH_SHORT).show();
            }
        });

        rvPokemonList.setAdapter(mPokemonAdapter);

        // ScrollListener to Trigger Infinite Loading
        setOnScrollListener();

        // Loads Pokemon List
        getPokemonList();
    }
    //endregion

    //region [ Private Methods ]
    private void getPokemonList() {
        mMainPresenter.loadPokemonList(mNextOffset, new PokemonListListener() {
            @Override
            public void onPokemonListLoad(PokemonList pokemonList) {
                displayPokemonList(pokemonList);
            }

            @Override
            public void onRequestStart() {
                showLoading();
            }

            @Override
            public void onRequestFinish() {
                hideLoading();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                hideLoading();
            }
        });
    }

    /**
     * Sets OnScrollListener for RecyclerView, so infinite loading can be handled
     */
    private void setOnScrollListener() {
        rvPokemonList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if ((mNextOffset > -1) && (dy > 0)) {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        getPokemonList();
                    }
                }
            }
        });
    }

    /**
     * Display Pokemon List in RecyclerView
     *
     * @param pokemonList - List of Pokemon read from API
     */
    private void displayPokemonList(PokemonList pokemonList) {
        mPokemonAdapter.addAll(pokemonList.results);
        mPokemonAdapter.notifyDataSetChanged();

        getNextOffset(pokemonList.next);
    }

    /**
     * Get next page to load on inifinite loading
     *
     * @param next - URL provided from API. Property Next from Object PokemonList
     */
    private void getNextOffset(String next) {
        if (next != null && next.length() > 0) {
            String[] array = next.split("offset=");
            mNextOffset = Integer.parseInt(array[array.length - 1]);
        } else {
            mNextOffset = -1;
        }
    }

    /**
     * Shows loading screen, if it' the first time it shows a fullScreen loading
     */
    private void showLoading() {
        if (isFirstLoad) {
            rlLoading.setVisibility(View.VISIBLE);
        } else {
            rlSmallLoading.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hides loading screen
     */
    private void hideLoading() {
        if (isFirstLoad) {
            rlLoading.setVisibility(View.GONE);
            isFirstLoad = false;
        } else {
            rlSmallLoading.setVisibility(View.GONE);
        }
    }
    //endregion
}
