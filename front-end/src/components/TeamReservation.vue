<template>
  <div>
    <h2>운동 시설 예약</h2>
    <ul>
      <li v-for="facility in facilities" :key="facility.id">
        {{ facility.name }} - {{ facility.available ? '예약 가능' : '예약 불가' }}
        <button @click="reserveFacility(facility.id)">예약하기</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      facilities: [
        { id: 1, name: "풋살장", available: true },
        { id: 2, name: "배드민턴장", available: false }
      ]
    };
  },
  methods: {
    reserveFacility(id) {
      console.log("시설 예약 ID:", id);
      // 예약 로직 추가
    }
  }
};
</script>
