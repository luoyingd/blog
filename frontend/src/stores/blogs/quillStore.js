import { makeAutoObservable } from "mobx";

class QuillStore {
  content = null;
  constructor() {
    makeAutoObservable(this);
  }
}

const quillStore = new QuillStore();
export default quillStore;
