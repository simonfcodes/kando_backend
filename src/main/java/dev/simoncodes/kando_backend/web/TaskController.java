4
    public void updateStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        var status = body.get("status");
        service.updateStatus(id, Task.Status.valueOf(status));
    }

    @PatchMapping("/{id}")
    public TaskDto updateTask(@PathVariable UUID id, @RequestBody UpdateTaskDto dto) {
        return service.updateTask(id, dto);
    }
}
