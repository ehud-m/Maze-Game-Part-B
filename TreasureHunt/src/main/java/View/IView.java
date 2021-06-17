package View;

import ViewModel.MyViewModel;

public interface IView {
    /**
     * sets view model on View
     * @param viewModel view of model
     */
    void setViewModel(MyViewModel viewModel);
}
