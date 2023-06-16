import { makeAutoObservable } from "mobx";

class Loading {
  isLoading = false;
  constructor() {
    makeAutoObservable(this);
  }
}

const loadingStore = new Loading();
export default loadingStore;
