<script setup>
// created 라이프사이클 훅에서 API 요청
import { onMounted, ref } from "vue";
import router from '@/router'
import apiService from "@/service/apiService";
import BoardFormComponent from "@/components/BoardFormComponent.vue";

let categoryNum = ref('')
let writer = ref('')
let pw = ref('')
let pwCheck = ref('')
let title = ref('')
let content = ref('')
let file = ref(null)
let file1 = ref(null)
let file2 = ref(null)


const writeContent = async () => {

  // 필수 입력 필드들의 유효성 검사 추가
  if (!categoryNum.value || !writer.value || !pw.value || !title.value || !content.value) {
    return;
  }

  try {

    const response = await apiService.postWrite(categoryNum, writer, pw, title, content)

    if (response && response.data&& response.data.boardNum) {

      const boardNum = response.data.boardNum;

      if((file.value && file.value.files[0]) || (file1.value && file1.value.files[0]) || (file2.value && file2.value.files[0])){
        const formData = new FormData();

        if (file.value && file.value.files[0]) {
          formData.append('files', file.value.files[0])
        }
        if (file1.value && file1.value.files[0]) {
          formData.append('files', file1.value.files[0])
        }
        if (file2.value && file2.value.files[0]) {
          formData.append('files', file2.value.files[0])
        }

        formData.append('boardNum', boardNum)

        await apiService.postFile(formData)
      }
      alert("게시물이 등록 되었습니다.")
      await router.push({ name: 'index' })
    } else {
      console.error("게시물 등록에 실패했습니다.")
    }
  }catch (error) {
      console.error('전송 에러:', error)
    }

  // 비밀번호 일치 여부 확인
  if (pw.value !== pwCheck.value) {
    alert('비밀번호가 일치하지 않습니다.')
  }
}

onMounted( async () => {
  await writeContent()
})

const pageIndex = () => {
  router.push({ name: 'index' })
}
</script>

<template>
  <div style="align-items: start">
    <h1>자유게시판 - 글작성</h1>
    <br />
    <div style="margin-top: 2%; margin-left: 5%">
      <div align="center" style="margin-top: 5%">
        <BoardFormComponent />
    </div>
  </div>
</template>