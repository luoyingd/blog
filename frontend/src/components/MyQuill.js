import ReactQuill, { Quill } from "react-quill";
import React from "react";
import quillStore from "../stores/blogs/quillStore";
import "react-quill/dist/quill.snow.css";
import { baseURL } from "../utils/http";
import uploadStore from "../stores/common/uploadStore";
import UploadPhoto from "./upload/uploadPhoto";

class MyQuill extends React.Component {
  modules = null;
  blogId = 0;
  constructor(props) {
    super(props);
    uploadStore.fileType = 0;
    this.reactQuillRef = null;
    this.modules = {
      toolbar: {
        container: [
          [{ size: ["small", false, "large", "huge"] }],
          ["bold", "italic", "underline", "strike"],
          [
            { list: "ordered" },
            { list: "bullet" },
            { indent: "-1" },
            { indent: "+1" },
          ],
          ["link", "image"],
          [{ align: [] }],
          [
            {
              background: [
                "rgb(  0,   0,   0)",
                "rgb(230,   0,   0)",
                "rgb(255, 153,   0)",
                "rgb(255, 255,   0)",
                "rgb(  0, 138,   0)",
                "rgb(  0, 102, 204)",
                "rgb(153,  51, 255)",
                "rgb(255, 255, 255)",
                "rgb(250, 204, 204)",
                "rgb(255, 235, 204)",
                "rgb(255, 255, 204)",
                "rgb(204, 232, 204)",
                "rgb(204, 224, 245)",
                "rgb(235, 214, 255)",
                "rgb(187, 187, 187)",
                "rgb(240, 102, 102)",
                "rgb(255, 194, 102)",
                "rgb(255, 255, 102)",
                "rgb(102, 185, 102)",
                "rgb(102, 163, 224)",
                "rgb(194, 133, 255)",
                "rgb(136, 136, 136)",
                "rgb(161,   0,   0)",
                "rgb(178, 107,   0)",
                "rgb(178, 178,   0)",
                "rgb(  0,  97,   0)",
                "rgb(  0,  71, 178)",
                "rgb(107,  36, 178)",
                "rgb( 68,  68,  68)",
                "rgb( 92,   0,   0)",
                "rgb(102,  61,   0)",
                "rgb(102, 102,   0)",
                "rgb(  0,  55,   0)",
                "rgb(  0,  41, 102)",
                "rgb( 61,  20,  10)",
              ],
            },
          ],
          [
            {
              color: [
                "rgb(  0,   0,   0)",
                "rgb(230,   0,   0)",
                "rgb(255, 153,   0)",
                "rgb(255, 255,   0)",
                "rgb(  0, 138,   0)",
                "rgb(  0, 102, 204)",
                "rgb(153,  51, 255)",
                "rgb(255, 255, 255)",
                "rgb(250, 204, 204)",
                "rgb(255, 235, 204)",
                "rgb(255, 255, 204)",
                "rgb(204, 232, 204)",
                "rgb(204, 224, 245)",
                "rgb(235, 214, 255)",
                "rgb(187, 187, 187)",
                "rgb(240, 102, 102)",
                "rgb(255, 194, 102)",
                "rgb(255, 255, 102)",
                "rgb(102, 185, 102)",
                "rgb(102, 163, 224)",
                "rgb(194, 133, 255)",
                "rgb(136, 136, 136)",
                "rgb(161,   0,   0)",
                "rgb(178, 107,   0)",
                "rgb(178, 178,   0)",
                "rgb(  0,  97,   0)",
                "rgb(  0,  71, 178)",
                "rgb(107,  36, 178)",
                "rgb( 68,  68,  68)",
                "rgb( 92,   0,   0)",
                "rgb(102,  61,   0)",
                "rgb(102, 102,   0)",
                "rgb(  0,  55,   0)",
                "rgb(  0,  41, 102)",
                "rgb( 61,  20,  10)",
              ],
            },
          ],
          ["clean"],
        ],
        handlers: {
          image: this.imageHandler.bind(this),
        },
      },
    };
  }
  imageHandler = () => {
    uploadStore.maxCount = 3;
    uploadStore.isShowModal = true;
    uploadStore.callBack = this.afterUpload;
  };
  afterUpload = (keys) => {
    console.log("Receive upload photo keys: ", keys);
    uploadStore.isShowModal = false;
    let quill = this.reactQuillRef.getEditor();
    keys.reverse();
    for (var key of keys) {
      console.log(key);
      quill.insertEmbed(
        quill.getSelection().index,
        "image",
        baseURL + "/common/photo/" + key,
        Quill.sources.Api
      );
    }
  };
  render() {
    return (
      <div>
        <UploadPhoto></UploadPhoto>
        <ReactQuill
          ref={(el) => {
            this.reactQuillRef = el;
          }}
          value={quillStore.content}
          key={this.props.key}
          theme="snow"
          modules={this.modules}
          onChange={(e) => {
            quillStore.content = e;
          }}
        />
      </div>
    );
  }
}

export default MyQuill;
