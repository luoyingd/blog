import { makeAutoObservable } from "mobx";

class ModalStore {
  isModalOpen = false;
  constructor() {
    makeAutoObservable(this);
  }
}

const modalStore = new ModalStore();
export default modalStore;
