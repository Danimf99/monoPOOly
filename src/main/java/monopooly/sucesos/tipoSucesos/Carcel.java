package monopooly.sucesos.tipoSucesos;

public class Carcel {
    private boolean encarcelado;

    public Carcel() {
        this.encarcelado = true;
    }

    public boolean saleDeCarcel() {
        return !encarcelado;
    }

    public boolean entraEnCarcel() {
        return encarcelado;
    }
}
