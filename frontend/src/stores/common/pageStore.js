import { makeAutoObservable } from "mobx";

class PageStore {
  page = 1;
  pageSize = 5;
  total = 0;
  pageChange = (page, size) => {
    if (page) {
      this.page = page;
    }
    if (size) {
      this.pageSize = size;
    }
  };

  constructor() {
    makeAutoObservable(this);
  }
}

const pageStore = new PageStore();
export default pageStore;
