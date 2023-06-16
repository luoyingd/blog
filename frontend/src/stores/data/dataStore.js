import { http } from "../../utils/http";
import { makeAutoObservable } from "mobx";

class MyDataStore {
  totalUser = 0;
  totalBlog = 0;
  myComments = 0;
  myBlog = 0;
  top5Topic = [];
  top5Author = [];
  loadTotal = async () => {
    const result = await http.get("/data/getTotal");
    this.totalUser = result.data.totalUser;
    this.totalBlog = result.data.totalBlog;
  };

  loadMy = async () => {
    const result = await http.get("/data/getMy");
    this.myComments = result.data.totalComment;
    this.myBlog = result.data.totalBlog;
  };

  loadTop5Topic = async () => {
    const result = await http.get("/data/getTOP5Topic");
    this.top5Topic = [];
    result.data.forEach((element) => {
      this.top5Topic.push({ name: element.topic, value: element.count });
    });
  };

  loadTop5Author = async () => {
    const result = await http.get("/data/getTOP5Author");
    this.top5Author = [];
    result.data.forEach((element) => {
      this.top5Author.push({ name: element.username, value: element.comments });
    });
  };

  constructor() {
    makeAutoObservable(this);
  }
}

const myDataStore = new MyDataStore();
export default myDataStore;
