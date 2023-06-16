import { http } from "../../utils/http";
import { makeAutoObservable } from "mobx";
import { myUser } from "../../utils/auth";

class CommentStore {
  isMine = false;
  content = "";
  replierId = 0;
  list = [];
  pageSize = 5;
  changeMine = (mine, blogId) => {
    this.isMine = mine;
    this.loadComment(1, blogId);
  };
  submitComment = async (blogId) => {
    await http.post("/comment", {
      content: this.content,
      senderId: myUser.getUserId(),
      replierId: this.replierId,
      blogId: blogId,
    });
  };
  deleteComment = async (commentId, blogId) => {
    await http.delete("/comment/" + blogId + "/" + commentId);
  };
  loadComment = async (index, blogId) => {
    const res = await http.get("/comment", {
      params: {
        blogId: blogId,
        pageIndex: index,
        pageSize: this.pageSize,
        senderId: this.isMine ? myUser.getUserId() : null,
      },
    });
    const data = res.data;
    if (index <= 1) {
      this.list = [];
    }
    data.forEach((element) => {
      element.createTime = new Date(element.createTime).toLocaleString();
      this.list.push(element);
    });
  };
  constructor() {
    makeAutoObservable(this);
  }
}

const commentStore = new CommentStore();
export default commentStore;
