import { http } from "../../utils/http";
import { makeAutoObservable } from "mobx";
import { myUser } from "../../utils/auth";
import pageStore from "../common/pageStore";
import quillStore from "./quillStore";

class BlogStore {
  blogs = [];
  myBlog = {
    title: null,
    content: null,
    author: null,
    authorPhoto: null,
    topic: null,
    likes: 0,
    createTime: null,
  };
  likeNow = false;
  loadBlogCount = async (values) => {
    let params = {
      authorId: values.authorId,
      topic: values.topic,
      title: values.title,
    };
    if (values.mine) {
      params.authorId = myUser.getUserId();
    }
    if (values.date) {
      params.startDate = values.date[0];
      params.endDate = values.date[1];
    }
    const res = await http.get("/blog/getBlogsCount", {
      params: params,
    });
    pageStore.total = res.data;
  };
  loadBlogs = async (values) => {
    let params = {
      authorId: values.authorId,
      topic: values.topic,
      page: pageStore.page,
      pageSize: pageStore.pageSize,
      title: values.title,
    };
    if (values.mine) {
      params.authorId = myUser.getUserId();
    }
    if (values.date) {
      params.startDate = values.date[0];
      params.endDate = values.date[1];
    }
    let res = await http.get("/blog/getBlogs", {
      params: {
        authorId: params.authorId,
        topic: params.topic,
        startDate: params.startDate,
        endDate: params.endDate,
        page: params.page,
        pageSize: params.pageSize,
        title: params.title,
      },
    });
    this.blogs = [];
    res.data.map((item) => {
      this.blogs.push({
        key: item.id,
        title: item.title,
        topic: item.topic,
        author: item.author,
        introduction: item.introduction,
        comments: item.comments,
        createTime: new Date(item.createTime).toLocaleString(),
        likes: item.likes,
        cover: item.cover,
      });
    });
  };
  loadBlogContent = async (id) => {
    const res = await http.get("/blog/getBlogContent/" + id);
    this.myBlog.authorPhoto = res.data.authorPhoto;
    this.myBlog.content = res.data.content;
    this.myBlog.createTime = new Date(res.data.createTime).toLocaleString();
    this.myBlog.likes = res.data.likes;
    this.myBlog.title = res.data.title;
    this.myBlog.topic = res.data.topic;
    this.myBlog.author = res.data.author;
  };
  loadBlogLikeCount = (id) => {
    http.get("/blog/getBlogLikeCount/" + id).then((res) => {
      this.myBlog.likes = res.data;
    });
  };
  loadLikeStatus = async (blogId) => {
    const res = await http.get(
      "/blog/getLikeStatus/" + blogId + "/" + myUser.getUserId()
    );
    this.likeNow = res.data;
  };
  alterLikeStatus = (blogId) => {
    console.log(this.likeNow);
    http
      .put("/blog/updateLike", {
        blogId: blogId,
        userId: myUser.getUserId(),
        like: this.likeNow,
      })
      .then(() => {
        this.loadBlogLikeCount(blogId);
      });
  };
  addOrUpdateBlog = async (values) => {
    const param = {
      userId: myUser.getUserId(),
      content: quillStore.content,
      title: values.title,
      topic: values.topic,
      introduction: values.introduction,
      cover: values.cover,
    };
    if (values.id) {
      param.id = values.id;
    }
    await http.post("/blog/addOrUpdate", param);
  };
  loadBlogDetail = async (id) => {
    const res = await http.get("/blog/getBlogDetail/" + id);
    return res.data;
  };
  delete = async (id) => {
    await http.delete("/blog/" + id);
  };
  constructor() {
    makeAutoObservable(this);
  }
}

const blogStore = new BlogStore();
export default blogStore;
