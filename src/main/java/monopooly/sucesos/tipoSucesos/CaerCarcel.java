package monopooly.sucesos.tipoSucesos;

public class CaerCarcel {
    private boolean encarcelado;

    public CaerCarcel() {
        this.encarcelado = true;
    }

    public boolean saleDeCarcel() {
        return !encarcelado;
    }

    public boolean entraEnCarcel() {
        return encarcelado;
    }
}
