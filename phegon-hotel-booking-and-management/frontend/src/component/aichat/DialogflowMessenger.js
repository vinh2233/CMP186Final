// src/component/common/DialogflowMessenger.js
import React, { useEffect } from "react";

const DialogflowMessenger = () => {
  useEffect(() => {
    // Tải script Dialogflow
    const script = document.createElement("script");
    script.src =
      "https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1";
    script.async = true;
    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script); // Xóa script khi component bị hủy
    };
  }, []);

  return (
    <df-messenger
      intent="WELCOME"
      chat-title="VITU-Helper"
      agent-id="243baae6-82de-4db2-983e-86dfd8529450"
      language-code="en"
    ></df-messenger>
  );
};

export default DialogflowMessenger;
