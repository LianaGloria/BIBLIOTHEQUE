package biblio.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import biblio.demo.model.Livre;

@Service
public class LivreService {
    public List<Livre> getlivres() {
        return Arrays.asList(
            new Livre("Le Petit Prince", "Saint-Exupéry"),
            new Livre("1984", "George Orwell"),
            new Livre("L’Étranger", "Albert Camus")
        );
    }
}

