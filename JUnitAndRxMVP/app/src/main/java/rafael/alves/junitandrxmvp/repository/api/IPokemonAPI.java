package rafael.alves.junitandrxmvp.repository.api;


import rafael.alves.junitandrxmvp.model.PokemonList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface IPokemonAPI {

    @GET("pokemon")
    Observable<PokemonList> getPokemonList(@Query("offset") int offset);
}
