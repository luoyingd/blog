import fileStore from "../../stores/common/fileStore";
import { Button, Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { observer } from "mobx-react-lite";
import uploadStore from "../../stores/common/uploadStore";
import { useEffect, useState } from "react";
function MyUpload({ maxCount, key}) {
  const [list, setList] = useState([]);
  const props = {
    name: "fileList",
    fileList: list,
    onChange(info) {
      setList(info.fileList);
      if (uploadStore.fileType === 0) {
        fileStore.quillFileList = info.fileList;
      } else if (uploadStore.fileType === 1) {
        fileStore.coverFileList = info.fileList;
      } else if (uploadStore.fileType === 2) {
        fileStore.avatarFileList = info.fileList;
      }
    },
  };
  const beforeUpload = (file) => {
    return false;
  };

  return (
    <div>
      <Upload
        {...props}
        beforeUpload={beforeUpload}
        maxCount={maxCount}
        multiple={maxCount > 1}
      >
        <Button icon={<UploadOutlined />} style={{ marginTop: "10px" }}>
          Upload - maximum {maxCount} files
        </Button>
      </Upload>
    </div>
  );
}

export default observer(MyUpload);
