import { observer } from "mobx-react-lite";
import uploadStore from "../../stores/common/uploadStore";
import fileStore from "../../stores/common/fileStore";
import { message, Modal, Spin } from "antd";
import MyUpload from "./myUpload";
const UploadPhoto = () => {
  const handleOk = async () => {
    let list = null;
    if (uploadStore.fileType === 0) {
      list = fileStore.quillFileList;
    } else if (uploadStore.fileType === 1) {
      list = fileStore.coverFileList;
    } else if (uploadStore.fileType === 2) {
      list = fileStore.avatarFileList;
    }
    const finish = fileStore.checkFile(list);
    if (finish === true) {
      fileStore.uploadFiles(uploadStore.callBack, list);
    } else {
      message.error(
        "You can only upload JPG/PNG/JPEG file, and each file should be smaller than 20MB!"
      );
    }
  };

  return (
    <Modal
      title="Upload Image"
      open={uploadStore.isShowModal}
      onOk={handleOk}
      onCancel={() => {
        uploadStore.isShowModal = false;
      }}
    >
      <MyUpload maxCount={uploadStore.maxCount} key={Math.random()}></MyUpload>
      <Spin spinning={fileStore.loading} style={{ marginLeft: "10px" }}></Spin>
    </Modal>
  );
};

export default observer(UploadPhoto);
